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
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new CustomException(UserErrorCode.EMAIL_ALREADY_EXISTS);
        }

        User user = userRepository.save(new User(request.password(), request.nickname(), request.email()));
        return new IdResponse(user.getId());
    }

    @Transactional
    public IdResponse updateUser(UpdateUserRequest request, Long authenticatedUserId) {
        User user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        user.update(request.password(), request.nickname());
        return new IdResponse(user.getId());
    }

    @Transactional
    public void deleteUser(Long authenticatedUserId) {
        User user = userRepository.findById(authenticatedUserId)
                .orElseThrow(() -> new CustomException(UserErrorCode.USER_NOT_FOUND));
        userRepository.delete(user);
    }
}
