package com.rookies4.myspringboot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

//StudentDetail 클래스
@Entity
@Table(name = "student_details")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDetail {
    //PK (Sequential Value)
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_detail_id")
    private Long id;

    //주소
    @Column(nullable = false)
    private String address;

    //전화번호
    @Column(nullable = false)
    private String phoneNumber;

    //이메일
    @Column(unique = true, nullable = false)
    private String email;

    //생년월일
    @Column
    private LocalDate dateOfBirth;

    //FK를 가진 엔티티가 주인(Owner)이다.
    //@JoinColumn은 FK(외래키)에 해당하는 어노테이션
    //1:1관계 지연로딩
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", unique = true)
    private Student student;
}