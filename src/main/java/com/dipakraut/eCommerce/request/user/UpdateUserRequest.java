package com.dipakraut.eCommerce.request.user;

import lombok.Data;
import org.hibernate.annotations.NaturalId;

@Data
public class UpdateUserRequest {

    private String firstName;
    private String lastName;

}
