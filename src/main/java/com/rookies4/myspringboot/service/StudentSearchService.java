package com.rookies4.myspringboot.service;

import com.rookies4.myspringboot.controller.dto.StudentDTO;
import com.rookies4.myspringboot.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class StudentSearchService {

    private final StudentRepository studentRepository;

    /**
     * 학생 검색 (이름 또는 학번으로)
     */
    public Page<StudentDTO.Response> searchStudents(String keyword, Pageable pageable) {
        if (!StringUtils.hasText(keyword)) {
            return studentRepository.findAll(pageable)
                    .map(StudentDTO.Response::fromEntity);
        }

        // 학번(StudentNumber) 검색 시도
        if (keyword.matches("^[A-Z]+\\d+$")) {
            return studentRepository.findByStudentNumberContainingIgnoreCase(keyword, pageable)
                    .map(StudentDTO.Response::fromEntity);
        }

        // 이름으로 검색
        return studentRepository.findByNameContainingIgnoreCase(keyword, pageable)
                .map(StudentDTO.Response::fromEntity);
    }

    /**
     * 학과별 학생 검색
     */
    public Page<StudentDTO.Response> searchStudentsByDepartment(Long departmentId, String keyword, Pageable pageable) {
        //학과ID로 검색
        if (!StringUtils.hasText(keyword)) {
            return studentRepository.findByDepartmentId(departmentId, pageable)
                    .map(StudentDTO.Response::fromEntity);
        }
        // 학과ID와 학생이름으로 검색
        return studentRepository.findByDepartmentIdAndNameContainingIgnoreCase(departmentId, keyword, pageable)
                .map(StudentDTO.Response::fromEntity);
    }

    /**
     * 이름으로 학생 검색
     */
    public Page<StudentDTO.Response> searchStudentsByName(String name, Pageable pageable) {
        return studentRepository.findByNameContainingIgnoreCase(name, pageable)
                .map(StudentDTO.Response::fromEntity);
    }

    /**
     * 학번으로 학생 검색
     */
    public Page<StudentDTO.Response> searchStudentsByStudentNumber(String studentNumber, Pageable pageable) {
        return studentRepository.findByStudentNumberContainingIgnoreCase(studentNumber, pageable)
                .map(StudentDTO.Response::fromEntity);
    }
}