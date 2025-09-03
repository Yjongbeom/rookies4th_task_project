package com.rookies4.myspringboot.service;

import com.rookies4.myspringboot.controller.dto.StudentDTO;
import com.rookies4.myspringboot.entity.Department;
import com.rookies4.myspringboot.entity.Student;
import com.rookies4.myspringboot.entity.StudentDetail;
import com.rookies4.myspringboot.exception.BusinessException;
import com.rookies4.myspringboot.exception.ErrorCode;
import com.rookies4.myspringboot.repository.DepartmentRepository;
import com.rookies4.myspringboot.repository.StudentDetailRepository;
import com.rookies4.myspringboot.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentService {

    private final StudentRepository studentRepository;
    private final StudentDetailRepository studentDetailRepository;
    private final DepartmentRepository departmentRepository;

    // 페이징 처리 없는 모든 학생 조회
    public List<StudentDTO.Response> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(StudentDTO.Response::fromEntity)
                .toList();
    }

    // 페이징 처리된 모든 학생 조회
    public Page<StudentDTO.Response> getAllStudents(Pageable pageable) {
        Page<Student> students = studentRepository.findAll(pageable);
        return students.map(StudentDTO.Response::fromEntity);
        //return students.map(entity -> StudentDTO.Response.fromEntity(entity));
    }

    public StudentDTO.Response getStudentById(Long id) {
        Student student = studentRepository.findByIdWithAllDetails(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Student", "id", id));
        return StudentDTO.Response.fromEntity(student);
    }

    public StudentDTO.Response getStudentByStudentNumber(String studentNumber) {
        Student student = studentRepository.findByStudentNumber(studentNumber)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Student", "student number", studentNumber));
        return StudentDTO.Response.fromEntity(student);
    }

    // 페이징 처리 없는 특정 학과의 학생 조회
    public List<StudentDTO.Response> getStudentsByDepartmentId(Long departmentId) {
        // Validate department exists
        if (!departmentRepository.existsById(departmentId)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                    "Department", "id", departmentId);
        }

        return studentRepository.findByDepartmentId(departmentId)
                .stream()
                .map(StudentDTO.Response::fromEntity)
                .toList();
    }

    // 페이징 처리된 특정 학과의 학생 조회
    public Page<StudentDTO.Response> getStudentsByDepartmentId(Long departmentId, Pageable pageable) {
        // Validate department exists
        if (!departmentRepository.existsById(departmentId)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                    "Department", "id", departmentId);
        }

        Page<Student> students = studentRepository.findByDepartmentId(departmentId, pageable);
        return students.map(StudentDTO.Response::fromEntity);
    }

    @Transactional
    public StudentDTO.Response createStudent(StudentDTO.Request request) {
        // Validate department exists
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Department", "id", request.getDepartmentId()));

        // Validate student number is not already in use
        if (studentRepository.existsByStudentNumber(request.getStudentNumber())) {
            throw new BusinessException(ErrorCode.STUDENT_NUMBER_DUPLICATE,
                    request.getStudentNumber());
        }

        // Validate email is not already in use (if provided)
        if (hasEmailAndExists(request.getDetailRequest())) {
            throw new BusinessException(ErrorCode.EMAIL_DUPLICATE,
                    request.getDetailRequest().getEmail());
        }

        // Validate phone number is not already in use
        if (hasDetailAndPhoneNumberExists(request.getDetailRequest())) {
            throw new BusinessException(ErrorCode.PHONE_NUMBER_DUPLICATE,
                    request.getDetailRequest().getPhoneNumber());
        }

        // Create student entity
        Student student = Student.builder()
                .name(request.getName())
                .studentNumber(request.getStudentNumber())
                .department(department)
                .build();

        // Create student detail if provided
        if (request.getDetailRequest() != null) {
            StudentDetail studentDetail = StudentDetail.builder()
                    .address(request.getDetailRequest().getAddress())
                    .phoneNumber(request.getDetailRequest().getPhoneNumber())
                    .email(request.getDetailRequest().getEmail())
                    .dateOfBirth(request.getDetailRequest().getDateOfBirth())
                    .student(student)
                    .build();

            student.setStudentDetail(studentDetail);
        }

        // Save and return the student
        Student savedStudent = studentRepository.save(student);
        return StudentDTO.Response.fromEntity(savedStudent);
    }

    @Transactional
    public StudentDTO.Response updateStudent(Long id, StudentDTO.Request request) {
        // Find the student
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Student", "id", id));

        // Validate department exists (if changing)
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                        "Department", "id", request.getDepartmentId()));

        // Check if another student already has the student number
        if (!student.getStudentNumber().equals(request.getStudentNumber()) &&
                studentRepository.existsByStudentNumber(request.getStudentNumber())) {
            throw new BusinessException(ErrorCode.STUDENT_NUMBER_DUPLICATE,
                    request.getStudentNumber());
        }

        // Update student basic info
        student.setName(request.getName());
        student.setStudentNumber(request.getStudentNumber());
        //학생과 학과 연결
        student.setDepartment(department);

        // Update student detail if provided
        if (request.getDetailRequest() != null) {
            StudentDetail studentDetail = student.getStudentDetail();

            // Create new detail if not exists
            // 등록할때 Student만 등록하고, StudentDetail을 등록하지 않은 경우의 Update
            if (studentDetail == null) {
                studentDetail = new StudentDetail();
                //수정하기 위해 입력받은 address로 StudentDetail 엔티티에 set 하기
                studentDetail.setAddress(request.getDetailRequest().getAddress());
                //수정하기 위해 입력받은 phoneNumber로 StudentDetail 엔티티에 set 하기
                studentDetail.setPhoneNumber(request.getDetailRequest().getPhoneNumber());
                //수정하기 위해 입력받은 email로 StudentDetail 엔티티에 set 하기
                studentDetail.setEmail(request.getDetailRequest().getEmail());
                //수정하기 위해 입력받은 dateOfBirth로 StudentDetail 엔티티에 set 하기
                studentDetail.setDateOfBirth(request.getDetailRequest().getDateOfBirth());
                //양방향 연관관계 설정
                studentDetail.setStudent(student);
                student.setStudentDetail(studentDetail);
            }

            // Validate email is not already in use (if changing)
            if (isEmailChangingAndExists(studentDetail, request.getDetailRequest())) {
                throw new BusinessException(ErrorCode.EMAIL_DUPLICATE,
                        request.getDetailRequest().getEmail());
            }

            // Validate phone number is not already in use (if changing)
            if (isPhoneNumberChangingAndExists(studentDetail, request.getDetailRequest())) {
                throw new BusinessException(ErrorCode.PHONE_NUMBER_DUPLICATE,
                        request.getDetailRequest().getPhoneNumber());
            }

            // Update detail fields
            studentDetail.setAddress(request.getDetailRequest().getAddress());
            studentDetail.setPhoneNumber(request.getDetailRequest().getPhoneNumber());
            studentDetail.setEmail(request.getDetailRequest().getEmail());
            studentDetail.setDateOfBirth(request.getDetailRequest().getDateOfBirth());
        }

        // Save and return updated student
        Student updatedStudent = studentRepository.save(student);
        return StudentDTO.Response.fromEntity(updatedStudent);
    }

    @Transactional
    public void deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.RESOURCE_NOT_FOUND,
                    "Student", "id", id);
        }
        studentRepository.deleteById(id);
    }

    // Helper methods to improve readability and reduce duplication

    private boolean hasEmailAndExists(StudentDTO.StudentDetailDTO detailRequest) {
        return detailRequest != null &&
                detailRequest.getEmail() != null &&
                !detailRequest.getEmail().isEmpty() &&
                studentDetailRepository.existsByEmail(detailRequest.getEmail());
    }

    private boolean hasDetailAndPhoneNumberExists(StudentDTO.StudentDetailDTO detailRequest) {
        return detailRequest != null &&
                studentDetailRepository.existsByPhoneNumber(detailRequest.getPhoneNumber());
    }

    private boolean isEmailChangingAndExists(StudentDetail currentDetail, StudentDTO.StudentDetailDTO newDetail) {
        return newDetail.getEmail() != null &&
                !newDetail.getEmail().isEmpty() &&
                (currentDetail.getEmail() == null ||
                        !currentDetail.getEmail().equals(newDetail.getEmail())) &&
                studentDetailRepository.existsByEmail(newDetail.getEmail());
    }

    private boolean isPhoneNumberChangingAndExists(StudentDetail currentDetail, StudentDTO.StudentDetailDTO newDetail) {
        return (currentDetail.getPhoneNumber() == null ||
                !currentDetail.getPhoneNumber().equals(newDetail.getPhoneNumber())) &&
                studentDetailRepository.existsByPhoneNumber(newDetail.getPhoneNumber());
    }
}