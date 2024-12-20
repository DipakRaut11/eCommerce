package com.dipakraut.eCommerce.data;

import com.dipakraut.eCommerce.model.User;
import com.dipakraut.eCommerce.repository.role.RoleRepository;
import com.dipakraut.eCommerce.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializerr implements ApplicationListener<ApplicationReadyEvent> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createDefaultUserIfNotExists();

    }

    private void createDefaultUserIfNotExists(){
        for (int i = 1; i<=5; i++){
            String defaultEmail = "user"+i+"@gmail.com";
            if (userRepository.existsByEmail(defaultEmail)){
                continue;
            }
            User user = new User();
            user.setFirstName("The user");
            user.setLastName("User" +i);
            user.setEmail(defaultEmail);
            user.setPassword(passwordEncoder.encode("123456"));
            userRepository.save(user);
            System.out.println("Default vet user "+i+"create successfully.");

        }

        private void createDefaultRoleIfNotExist(Set<String> roles) {
            roles.stream()
                    .filter(role -> roleRepository.findByName(role).isEmpty())
                    .map(Role::new)
                    .forEach(roleRepository::save);
        }



    }

}
