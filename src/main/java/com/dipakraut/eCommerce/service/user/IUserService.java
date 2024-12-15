package com.dipakraut.eCommerce.service.user;

import com.dipakraut.eCommerce.dto.user.UserDto;
import com.dipakraut.eCommerce.model.User;
import com.dipakraut.eCommerce.request.user.CreateUserRequest;
import com.dipakraut.eCommerce.request.user.UpdateUserRequest;

public interface IUserService {
    User getUserById(long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, long userId);
    void deleteUser(long userId);

    UserDto convertUserToDto(User userId);
}
