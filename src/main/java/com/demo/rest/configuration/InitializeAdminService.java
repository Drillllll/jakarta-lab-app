package com.demo.rest.configuration;

import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.player.entity.PlayerRoles;
import com.demo.rest.modules.player.repository.api.PlayerRepository;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.ejb.TransactionAttribute;
import jakarta.ejb.TransactionAttributeType;
import jakarta.inject.Inject;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * EJB singleton can be forced to start automatically when application starts. Injects proxy to the services and fills
 * database with default content. When using persistence storage application instance should be initialized only during
 * first run in order to init database with starting data. Good place to create first default admin user.
 */
@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.REQUIRED)
@NoArgsConstructor(force = true)
public class InitializeAdminService {


    private final PlayerRepository playerRepository;

    private final Pbkdf2PasswordHash passwordHash;


    @Inject
    public InitializeAdminService(
            PlayerRepository playerRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") Pbkdf2PasswordHash passwordHash
    ) {
        this.playerRepository = playerRepository;
        this.passwordHash = passwordHash;
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should be
     * created only once.
     */
    @PostConstruct
    @SneakyThrows
    private void init() {
        if (playerRepository.findByLogin("admin-service").isEmpty()) {

            Player admin = Player.builder()
                    .id(UUID.fromString("14d59f3a-057c-44d5-825a-19295a6600a8"))
                    .login("admin-service")
                    .username("Admin")
                    .birthDate(LocalDate.of(1990, 10, 21))
                    .password(passwordHash.generate("adminadmin".toCharArray()))
                    .roles(List.of(PlayerRoles.ADMIN, PlayerRoles.PLAYER))
                    .build();

            playerRepository.create(admin);
        }
    }

}
