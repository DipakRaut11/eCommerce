package com.dipakraut.eCommerce.service.user;

import com.dipakraut.eCommerce.exception.ResourceNotFoundException;
import com.dipakraut.eCommerce.model.User;
import com.dipakraut.eCommerce.repository.user.UserRepository;
import com.dipakraut.eCommerce.request.user.CreateUserRequest;
import com.dipakraut.eCommerce.request.user.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;

    @Override
    public User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return null;
    }

    @Override
    public User updateUser(UpdateUserRequest request, long userId) {
        return userRepository.findById(userId)
                .map(existingUser ->{
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository :: delete, () ->{
                    throw new ResourceNotFoundException("User not found with id: " + userId);
                });
    }
}
