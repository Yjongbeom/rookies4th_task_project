package com.rookies4.myspringboot.controller.dto;

import com.rookies4.myspringboot.entity.UserEntity;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class UserDTO {
    
    //등록 데이터 입력 DTO Inner class
    @Getter
    @Setter
    public static class UserCreateRequest {
        @NotBlank(message = "Name은 필수 입력항목입니다.")
        private String name;
        
        @NotBlank(message = "Email은 필수 입력항목입니다.")
        private String email;

        // DTO를 Entity로 변환하는 메서드
        public UserEntity toEntity() {
            UserEntity user = new UserEntity();
            user.setName(this.name);
            user.setEmail(this.email);
            return user;
        }
    }
    
    //수정 데이터 입력 DTO Inner class
    @Getter
    @Setter
    public static class UserUpdateRequest {
        @NotBlank(message = "Name 은 필수 입력항목입니다.")
        private String name;
    }
    
    //조회한 데이터 출력 DTO Inner class
    @Getter
    public static class UserResponse {
        private Long id;
        private String name;
        private String email;
        private LocalDateTime createdAt;
        
        public UserResponse(UserEntity user) {
            this.id = user.getId();
            this.name = user.getName();
            this.email = user.getEmail();
            this.createdAt = user.getCreatedAt();
        }

//        public LocalDateTime getCreatedAt() {
//            return createdAt;
//        }
    }
}