package com.rookies4.myspringboot.controller.dto;

import com.rookies4.myspringboot.entity.Department;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

public class DepartmentDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Request {
        @NotBlank(message = "Department name is required")
        @Size(max = 100, message = "Department name cannot exceed 100 characters")
        private String name;

        @NotBlank(message = "Department code is required")
        @Size(max = 10, message = "Department code cannot exceed 10 characters")
        private String code;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private Long id;
        private String name;
        private String code;
        private Long studentCount;
        private List<StudentDTO.SimpleResponse> students;

        public static Response fromEntity(Department department) {
            return Response.builder()
                    .id(department.getId())
                    .name(department.getName())
                    .code(department.getCode())
                    .studentCount((long) department.getStudents().size())
                    .students(department.getStudents().stream()
                            .map(StudentDTO.SimpleResponse::fromEntity)
                            .collect(Collectors.toList()))
                    .build();
        }

    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SimpleResponse {
        private Long id;
        private String name;
        private String code;
        private Long studentCount;

        public static SimpleResponse fromEntity(Department department) {
            return SimpleResponse.builder()
                    .id(department.getId())
                    .name(department.getName())
                    .code(department.getCode())
                    .studentCount((long) department.getStudents().size())
                    .build();
        }
    }
}