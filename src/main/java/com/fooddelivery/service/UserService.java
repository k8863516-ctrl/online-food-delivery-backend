package com.fooddelivery.service;

import com.fooddelivery.dto.UserDTO;
import com.fooddelivery.entity.User;
import com.fooddelivery.exception.ResourceNotFoundException;
import com.fooddelivery.repository.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getCurrentAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication.getName() == null) {
            throw new ResourceNotFoundException("No authenticated user in context");
        }
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + email));
    }

    public UserDTO getCurrentUserProfile() {
        return new UserDTO(getCurrentAuthenticatedUser());
    }

    public UserDTO updateUserProfile(UserDTO updateReq) {
        User user = getCurrentAuthenticatedUser();
        if (updateReq.getName() != null && !updateReq.getName().isBlank()) {
            user.setName(updateReq.getName());
        }
        if (updateReq.getPhone() != null) {
            user.setPhone(updateReq.getPhone());
        }
        if (updateReq.getAddress() != null) {
            user.setAddress(updateReq.getAddress());
        }
        User saved = userRepository.save(user);
        return new UserDTO(saved);
    }

    public List<UserDTO> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDTO::new)
                .collect(Collectors.toList());
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }
}
