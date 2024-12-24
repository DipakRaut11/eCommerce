package com.dipakraut.eCommerce.service.user;

import com.dipakraut.eCommerce.dto.order.OrderDto;
import com.dipakraut.eCommerce.dto.user.UserDto;
import com.dipakraut.eCommerce.exception.ResourceNotFoundException;
import com.dipakraut.eCommerce.model.User;
import com.dipakraut.eCommerce.repository.order.OrderRepository;
import com.dipakraut.eCommerce.repository.user.UserRepository;
import com.dipakraut.eCommerce.request.user.CreateUserRequest;
import com.dipakraut.eCommerce.request.user.UpdateUserRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final OrderRepository orderRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserById(long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Override
    public User createUser(CreateUserRequest request) {
        return Optional.of(request)
                .filter(user -> !userRepository
                        .existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(passwordEncoder.encode(request.getPassword()));
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new ResourceNotFoundException("User already exists with email: " + request.getEmail()));
    }

    @Override
    public User updateUser(UpdateUserRequest request, long userId) {
        return userRepository.findById(userId)
                .map(existingUser -> {
                    existingUser.setFirstName(request.getFirstName());
                    existingUser.setLastName(request.getLastName());
                    return userRepository.save(existingUser);
                }).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
    }

    @Override
    public void deleteUser(long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete, () -> {
                    throw new ResourceNotFoundException("User not found with id: " + userId);
                });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        /*
        UserDto userDto = modelMapper.map(user, UserDto.class);

        // Fetch orders associated with the user
        List<OrderDto> orderDtos = orderRepository.findByUserId(user.getId())
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class)) // Map each Order to OrderDto
                .toList();

        // Set orders in UserDto
        userDto.setOrders(orderDtos);

        return userDto;

         */
        return modelMapper.map(user, UserDto.class);
    }

    @Override
    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return userRepository.findByEmail(email);
    }
}


