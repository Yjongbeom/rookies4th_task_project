package com.rookies4.myspringboot.runner;

import com.rookies4.myspringboot.entity.Department;
import com.rookies4.myspringboot.entity.Student;
import com.rookies4.myspringboot.entity.StudentDetail;
import com.rookies4.myspringboot.repository.DepartmentRepository;
import com.rookies4.myspringboot.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Component
@Order(1)
@RequiredArgsConstructor
@Slf4j
public class DataInitRunner implements CommandLineRunner {

    private final DepartmentRepository departmentRepository;
    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        log.info("Starting data initialization...");

        // Check if data already exists
        if (departmentRepository.count() > 0) {
            log.info("Data already exists, skipping initialization");
            return;
        }

        // Create departments
        List<Department> departments = createDepartments();
        
        // Create students
        createStudents(departments);

        log.info("Data initialization completed successfully");
    }

    private List<Department> createDepartments() {
        log.info("Creating departments...");

        Department computerScience = Department.builder()
                .name("Computer Science")
                .code("CS")
                .build();

        Department electricalEngineering = Department.builder()
                .name("Electrical Engineering")
                .code("EE")
                .build();

        Department mechanicalEngineering = Department.builder()
                .name("Mechanical Engineering")
                .code("ME")
                .build();

        Department businessAdministration = Department.builder()
                .name("Business Administration")
                .code("BA")
                .build();

        List<Department> departments = departmentRepository.saveAll(
                List.of(computerScience, electricalEngineering, mechanicalEngineering, businessAdministration)
        );

        log.info("Created {} departments", departments.size());
        return departments;
    }

    private void createStudents(List<Department> departments) {
        log.info("Creating students...");

        Department cs = departments.get(0);
        Department ee = departments.get(1);
        Department me = departments.get(2);
        Department ba = departments.get(3);

        // Computer Science students
        Student student1 = createStudentWithDetail(
                "Alice Johnson", "CS001", cs,
                "123 Tech Street", "010-1234-5678", "alice@example.com",
                LocalDate.of(1998, 3, 15)
        );

        Student student2 = createStudentWithDetail(
                "Bob Smith", "CS002", cs,
                "456 Code Avenue", "010-2345-6789", "bob@example.com",
                LocalDate.of(1997, 7, 22)
        );

        // Electrical Engineering students
        Student student3 = createStudentWithDetail(
                "Charlie Brown", "EE001", ee,
                "789 Circuit Lane", "010-3456-7890", "charlie@example.com",
                LocalDate.of(1999, 11, 8)
        );

        Student student4 = createStudentWithDetail(
                "Diana Wilson", "EE002", ee,
                "321 Power Street", "010-4567-8901", "diana@example.com",
                LocalDate.of(1998, 5, 30)
        );

        // Mechanical Engineering students
        Student student5 = createStudentWithDetail(
                "Edward Davis", "ME001", me,
                "654 Engine Road", "010-5678-9012", "edward@example.com",
                LocalDate.of(1997, 12, 12)
        );

        // Business Administration students
        Student student6 = createStudentWithDetail(
                "Fiona Garcia", "BA001", ba,
                "987 Business Plaza", "010-6789-0123", "fiona@example.com",
                LocalDate.of(1999, 2, 28)
        );

        Student student7 = createStudentWithDetail(
                "George Martinez", "BA002", ba,
                "147 Commerce Street", "010-7890-1234", "george@example.com",
                LocalDate.of(1998, 9, 10)
        );

        // Student without detail (Computer Science)
        Student student8 = Student.builder()
                .name("Helen Lee")
                .studentNumber("CS003")
                .department(cs)
                .build();

        List<Student> students = studentRepository.saveAll(
                List.of(student1, student2, student3, student4, student5, student6, student7, student8)
        );

        log.info("Created {} students", students.size());
    }

    private Student createStudentWithDetail(String name, String studentNumber, Department department,
                                         String address, String phoneNumber, String email, LocalDate dateOfBirth) {
        //StudentDetail
        StudentDetail detail = StudentDetail.builder()
                .address(address)
                .phoneNumber(phoneNumber)
                .email(email)
                .dateOfBirth(dateOfBirth)
                .build();
        //Student
        Student student = Student.builder()
                .name(name)
                .studentNumber(studentNumber)
                //양방향 연관관계 설정
                .department(department)
                //양방향 연관관계 설정
                .studentDetail(detail)
                .build();

        //양방향 연관관계 설정
        detail.setStudent(student);
        return student;
    }
}