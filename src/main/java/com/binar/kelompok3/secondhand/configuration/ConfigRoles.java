package com.binar.kelompok3.secondhand.configuration;

import com.binar.kelompok3.secondhand.model.Roles;
import com.binar.kelompok3.secondhand.enumeration.ERole;
import com.binar.kelompok3.secondhand.repository.RolesRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AllArgsConstructor
public class ConfigRoles {

    private static final Logger logger = LoggerFactory.getLogger(ConfigRoles.class);

    private RolesRepository rolesRepository;

    @Bean
    public void prerun() {
        for (ERole role : ERole.values()) {
            try {
                Roles roles = rolesRepository.findByName(role).orElseThrow(() -> new RuntimeException("Roles not found"));
                logger.info("Role {} has been found!", roles.getName());
            } catch (RuntimeException rte) {
                logger.info("Role {} is not found, inserting to database . . .", role.name());
                Roles roles = new Roles();
                roles.setName(role);
                rolesRepository.save(roles);
            }
        }
    }
}
