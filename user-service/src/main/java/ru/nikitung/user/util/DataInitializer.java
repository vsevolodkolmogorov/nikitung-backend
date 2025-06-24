package ru.nikitung.user.util;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import ru.nikitung.user.model.Role;
import ru.nikitung.user.model.enums.RoleCode;
import ru.nikitung.user.repository.RoleRepository;

@Component
public class DataInitializer implements ApplicationRunner {

    private final RoleRepository roleRepository;

    public DataInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        createRoleIfNotExists(RoleCode.USER);
        createRoleIfNotExists(RoleCode.ADMIN);
    }

    private void createRoleIfNotExists(RoleCode roleCode) {
        roleRepository.findByCode(roleCode.name()).orElseGet(() -> {
            Role role = new Role();
            role.setCode(roleCode.name());
            return roleRepository.save(role);
        });
    }
}
