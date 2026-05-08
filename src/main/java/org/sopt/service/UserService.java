package org.sopt.service;

import org.sopt.domain.User;
import org.sopt.dto.request.CreateUserRequest;
import org.sopt.dto.request.UpdateUserRequest;
import org.sopt.dto.response.IdResponse;
import org.sopt.dto.response.UserResponse;
import org.sopt.exception.CustomException;
import org.sopt.exception.UserErrorCode;
import org.sopt.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public IdResponse createUser(CreateUserRequest request) {
        User user = userRepository.save(new User(request.nickname(), request.email()));
        return new IdResponse(user.getId());
    }

    @Transactional(readOnly = true)
    public UserResponse getUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        return new UserResponse(user);
    }

    @Transactional
    public IdResponse updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        user.update(request.nickname());
        return new IdResponse(user.getId());
    }

    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }
}
