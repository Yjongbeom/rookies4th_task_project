package com.rookies4.myspringboot.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "departments")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Department {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Long id;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    @Column(unique = true, nullable = false)
    private String code;

    //Builder 패턴을 적용했을 때 변수에 명시적으로 초기화 한 값이 유지 되도록 해주는 어노테이션
    @Builder.Default
    //Department에서 Student를 참조할 수 있도록 FK에 해당하는 필드명을 mappedBy에 설정한다.
    @OneToMany(mappedBy = "department", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Student> students = new ArrayList<>();
    
    // Helper methods
    public void addStudent(Student student) {
        students.add(student);
        student.setDepartment(this);
    }
    
    public void removeStudent(Student student) {
        students.remove(student);
        student.setDepartment(null);
    }
}