package com.rookies4.myspringboot.service;

import com.rookies4.myspringboot.controller.dto.UserDTO;
import com.rookies4.myspringboot.entity.UserEntity;
import com.rookies4.myspringboot.exception.BusinessException;
import com.rookies4.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    private final UserRepository userRepository;

    //User 등록
    @Transactional
    public UserDTO.UserResponse createUser(UserDTO.UserCreateRequest request) {
        //Email이 중복되면 BizException 발생 시키고 종료
        userRepository.findByEmail(request.getEmail()) //Optional<UserEntity>
                .ifPresent(entity -> {
                    throw new BusinessException("User with this Email already Exist", HttpStatus.CONFLICT);
                });
        //DTO => Entity 로 변환
        UserEntity entity = request.toEntity();
        UserEntity savedEntity = userRepository.save(entity);
        //Entity => DTO로 변환 후 리턴됨
        return new UserDTO.UserResponse(savedEntity);
    }

    //Id로 User 조회하기
    public UserDTO.UserResponse getUserById(Long id){
        UserEntity userEntity = getUserExist(id);
        return new UserDTO.UserResponse(userEntity);
    }

    //User 목록 조회하기
    public List<UserDTO.UserResponse> getAllUsers() {
        //List<UserEntity> => List<UserDTO.UserResponse>
        //Level1
//        return userRepository.findAll() //List<UserEntity>
//                .stream() //Stream<UserEntity>
//                .map(entity -> new UserDTO.UserResponse(entity)) //Stream<UserDTO.UserResponse>
//                .collect(Collectors.toList()); //List<UserDTO.UserResponse>
        //Level2
          return  userRepository.findAll()
                  .stream()
                  .map(UserDTO.UserResponse::new)
                  .toList();
    }

    //User 수정
    @Transactional
    public UserDTO.UserResponse updateUser(String email,
                                           UserDTO.UserUpdateRequest request){
        UserEntity existUser = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        //dirty read ( setter 메서드만 호출하고, save() 메서드는 호출하지 않아도 됨)
        existUser.setName(request.getName());
        return new UserDTO.UserResponse(existUser);
    }
    //User 삭제
    @Transactional
    public void deleteUser(Long id){
        UserEntity userEntity = getUserExist(id);
        userRepository.delete(userEntity);
    }

    //내부 Helper Method
    private UserEntity getUserExist(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
    }



}
