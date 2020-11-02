package com.dev.cinema.service;

import com.dev.cinema.model.Role;
import com.dev.cinema.model.User;
import java.util.Set;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Service;

@Service
public class InjectData {
    private final UserService userService;
    private final RoleService roleService;
    private final ShoppingCartService shoppingCartService;

    public InjectData(UserService userService, RoleService roleService,
                      ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.roleService = roleService;
        this.shoppingCartService = shoppingCartService;
    }

    @PostConstruct
    private void injectData() {
        Role roleAdmin = Role.of("ADMIN");
        Role roleUser = Role.of("USER");
        roleService.add(roleAdmin);
        roleService.add(roleUser);
        User admin = new User();
        admin.setEmail("admin");
        admin.setPassword("1234");
        admin.setRoles(Set.of(roleAdmin));
        User simpleUser = new User();
        simpleUser.setEmail("alise@mail.com");
        simpleUser.setPassword("1111");
        simpleUser.setRoles(Set.of(roleUser));
        User adminAndSimple = new User();
        adminAndSimple.setEmail("bob@mail.com");
        adminAndSimple.setPassword("1111");
        adminAndSimple.setRoles(Set.of(roleAdmin, roleUser));
        userService.add(admin);
        userService.add(adminAndSimple);
        userService.add(simpleUser);
        shoppingCartService.registerNewShoppingCart(simpleUser);
        shoppingCartService.registerNewShoppingCart(adminAndSimple);
    }
}
