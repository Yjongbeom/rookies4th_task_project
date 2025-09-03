package com.rookies4.myspringboot.repository;

import com.rookies4.myspringboot.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//StudentRepository 인터페이스
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    //학번으로 조회하는 Query Method
    Optional<Student> findByStudentNumber(String studentNumber);
    //Fetch Join 을 사용하여 1개의 Query 만 생성이 되도록 처리함
    @Query("SELECT s FROM Student s JOIN FETCH s.studentDetail WHERE s.id = :id")
    Optional<Student> findByIdWithStudentDetail(@Param("id") Long studentId);
    //학번의 중복체크를 위한 메서드
    boolean existsByStudentNumber(String studentNumber);

    //Department 관련해서 새로 추가된 메서드
    @Query("SELECT s FROM Student s LEFT JOIN FETCH s.studentDetail LEFT JOIN FETCH s.department WHERE s.id = :id")
    Optional<Student> findByIdWithAllDetails(@Param("id") Long studentId);

    // 새로 추가된 메서드
    List<Student> findByDepartmentId(Long departmentId);

    // 새로 추가된 메서드
    @Query("SELECT COUNT(s) FROM Student s WHERE s.department.id = :departmentId")
    Long countByDepartmentId(@Param("departmentId") Long departmentId);

    //Paging 과 Search(검색) 관련 메서드들
    // 특정학과에 속한 학생의 ID로 조회 (페이징)
    Page<Student> findByDepartmentId(Long departmentId, Pageable pageable);

    // 학생 이름으로 검색 (페이징) '% name %'
    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // 학생 번호로 검색 (페이징)
    Page<Student> findByStudentNumberContainingIgnoreCase(String studentNumber, Pageable pageable);

    // 특정학과에 속한 학생의 이름 검색 (페이징)
    Page<Student> findByDepartmentIdAndNameContainingIgnoreCase(Long departmentId,
                                                                String name, Pageable pageable);
}