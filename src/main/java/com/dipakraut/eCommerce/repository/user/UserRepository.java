package com.dipakraut.eCommerce.repository.user;

import com.dipakraut.eCommerce.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);
}
