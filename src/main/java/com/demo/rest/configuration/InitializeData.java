package com.demo.rest.configuration;

import com.demo.rest.player.entity.Player;
import com.demo.rest.player.service.PlayerService;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.UUID;




@WebListener
public class InitializeData implements ServletContextListener {

    PlayerService playerService;

    @Override
    public void contextInitialized(ServletContextEvent event) {
        playerService = (PlayerService) event.getServletContext().getAttribute("playerService");

        init();
    }

    /**
     * Initializes database with some example values. Should be called after creating this object. This object should be
     * created only once.
     */
    @SneakyThrows
    private void init() {
        Player player1 = Player.builder()
                .id(UUID.fromString("88dd8d51-8456-42ce-b6a0-61b2ed3e5e21"))
                .login("adventurer@game.com")
                .password("iLoveKillingDragons&!")
                .username("ADV3nturer")
                .birthDate(LocalDate.of(2000, 10, 10))
                .heroName("Dragon Slayer")
                .build();

        Player player2 = Player.builder()
                .id(UUID.fromString("d59d1288-b330-43f5-b519-3537c7b84806"))
                .login("rpg-enjoyer@game.com")
                .password("magicIsInTheAir123#!")
                .username("RpgEnjoy3r")
                .birthDate(LocalDate.of(2004, 2, 1))
                .heroName("Powerful Mage")
                .build();

        Player player3 = Player.builder()
                .id(UUID.fromString("665e4aba-0640-49c2-b71f-4ddf1f9674ba"))
                .login("swoard-to-the-throat@game.com")
                .password("iWill!burry)YouALivE#")
                .username("SwordToTh3THROAT")
                .birthDate(LocalDate.of(2003, 7, 17))
                .heroName("Ruthless Assassin")
                .build();

        Player player4 = Player.builder()
                .id(UUID.fromString("944cc65e-1942-4c8a-a12e-cfffeef94476"))
                .login("one-shot-one-kill@game.com")
                .password("aim!!+Twice#Shoot#Once")
                .username("OneSh0T1Kill")
                .birthDate(LocalDate.of(2004, 2, 1))
                .heroName("Patient Archer")
                .build();

        playerService.create(player1);
        playerService.create(player2);
        playerService.create(player3);
        playerService.create(player4);

    }


    /**
     * @param name name of the desired resource
     * @return array of bytes read from the resource
     */
    @SneakyThrows
    private byte[] getResourceAsByteArray(String name) {
        try (InputStream is = this.getClass().getResourceAsStream(name)) {
            if (is != null) {
                return is.readAllBytes();
            } else {
                throw new IllegalStateException("Unable to get resource %s".formatted(name));
            }
        }
    }
}
