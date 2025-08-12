package com.cardmanagement.user_service.service;

import com.cardmanagement.user_service.dto.UserRequest;
import com.cardmanagement.user_service.dto.UserResponse;
import com.cardmanagement.user_service.model.User;

import java.util.List;

public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    UserResponse getUserById(Long id);

    UserResponse getUserByEmail(String email);

    List<UserResponse> getAllUsers();

    List<UserResponse> getUsersByStatus(User.UserStatus status);

    UserResponse updateUser(Long id, UserRequest userRequest);

    void deleteUser(Long id);

    UserResponse updateUserStatus(Long id, User.UserStatus status);

    List<UserResponse> searchUsersByName(String name);
}
