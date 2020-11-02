package com.dev.cinema.security;

import com.dev.cinema.model.User;
import com.dev.cinema.service.UserService;
import java.util.Optional;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userService.findByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserBuilder builder =
                    org.springframework.security.core.userdetails.User.withUsername(email);
            builder.password(user.getPassword());
            String[] userRoles = user.getRoles().stream()
                    .map(r -> r.getRoleName().toString())
                    .toArray(String[]::new);
            builder.roles(userRoles);
            return builder.build();
        } else {
            throw new UsernameNotFoundException("User not found");
        }
    }
}
