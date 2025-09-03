package com.rookies4.myspringboot.repository;

import com.rookies4.myspringboot.entity.StudentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

//StudentDetailRepository 인터페이스
@Repository
public interface StudentDetailRepository extends JpaRepository<StudentDetail, Long> {
    //Student의 PK로 StudentDetail 정보를 조회하는 Query Method
    Optional<StudentDetail> findByStudentId(Long studentId);

    //Fetch Join 을 사용하여 1개의 Query 만 생성이 되도록 처리함
    @Query("SELECT sd FROM StudentDetail sd JOIN FETCH sd.student WHERE sd.id = :id")
    Optional<StudentDetail> findByIdWithStudent(@Param("id") Long studentDetailId);
    //전화번호 중복체크를 위한 메서드
    boolean existsByPhoneNumber(String phoneNumber);
    //이메일의 중복체크를 위한 메서드
    boolean existsByEmail(String email);
}