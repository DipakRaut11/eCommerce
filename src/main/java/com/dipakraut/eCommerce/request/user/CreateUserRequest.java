package com.dipakraut.eCommerce.request.user;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
public class CreateUserRequest {

    private String firstName;
    private String lastName;
    @NaturalId
    private String email;
    private String password;
}
