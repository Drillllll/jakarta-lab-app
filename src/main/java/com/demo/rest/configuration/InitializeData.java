package com.demo.rest.configuration;

import com.demo.rest.modules.player.entity.PlayerRoles;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.ejb.*;
import jakarta.annotation.security.RunAs;
import lombok.NoArgsConstructor;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.player.service.PlayerService;
import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapon.service.WeaponService;
import com.demo.rest.modules.weapontype.entity.DamageType;
import com.demo.rest.modules.weapontype.entity.WeaponType;
import com.demo.rest.modules.weapontype.service.WeaponTypeService;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@NoArgsConstructor
@DependsOn("InitializeAdminService")
@DeclareRoles({PlayerRoles.ADMIN, PlayerRoles.PLAYER})
@RunAs(PlayerRoles.ADMIN)
public class InitializeData {

    private PlayerService playerService;
    private WeaponService weaponService;
    private WeaponTypeService weaponTypeService;

    @Inject
    private SecurityContext securityContext;

    @EJB
    public void setPlayerService(PlayerService service) {
        this.playerService = service;
    }

    @EJB
    public void setWeaponService(WeaponService service) {
        this.weaponService = service;
    }

    @EJB
    public void setWeaponTypeService(WeaponTypeService service) {
        this.weaponTypeService = service;
    }


    /**
     * Initializes database with some example values. Should be called after creating this object. This object should be
     * created only once.
     */
    @PostConstruct
    @SneakyThrows
    private void init() {


        Player admin = Player.builder()
                .id(UUID.fromString("0cc37711-5496-474c-b22c-ea0553d29979"))
                .login("admin")
                .password("admin")
                .username("admin")
                .birthDate(LocalDate.of(2004, 1, 1))
                .heroName("admin")
                .roles(List.of(PlayerRoles.ADMIN, PlayerRoles.PLAYER))
                .build();

        Player warrior = Player.builder()
                .id(UUID.fromString("88dd8d51-8456-42ce-b6a0-61b2ed3e5e21"))
                .login("warrior@game.com")
                .password("iLoveKillingDragons&!")
                .username("ADV3nturer")
                .birthDate(LocalDate.of(2000, 10, 10))
                .heroName("Dragon Slayer")
                .roles(List.of(PlayerRoles.PLAYER))
                .build();

        Player mage = Player.builder()
                .id(UUID.fromString("d59d1288-b330-43f5-b519-3537c7b84806"))
                .login("mage@game.com")
                .password("magicIsInTheAir123#!")
                .username("RpgEnjoy3r")
                .birthDate(LocalDate.of(2004, 2, 1))
                .heroName("Powerful Mage")
                .roles(List.of(PlayerRoles.PLAYER))
                .build();

        Player assassin = Player.builder()
                .id(UUID.fromString("665e4aba-0640-49c2-b71f-4ddf1f9674ba"))
                .login("assassin@game.com")
                .password("iWill!burry)YouALivE#")
                .username("SwordToTh3THROAT")
                .birthDate(LocalDate.of(2003, 7, 17))
                .heroName("Ruthless Assassin")
                .roles(List.of(PlayerRoles.PLAYER))
                .build();

        Player archer = Player.builder()
                .id(UUID.fromString("944cc65e-1942-4c8a-a12e-cfffeef94476"))
                .login("archer@game.com")
                .password("aim!!+Twice#Shoot#Once")
                .username("OneSh0T1Kill")
                .birthDate(LocalDate.of(2004, 2, 1))
                .heroName("Patient Archer")
                .roles(List.of(PlayerRoles.PLAYER))
                .build();



        WeaponType twoHandedSword = WeaponType.builder()
                .id(UUID.fromString("ef47b366-7352-417f-a232-c530e65d7226"))
                .name("Two Handed Sword")
                .speed(2.0f)
                .isTwoHanded(true)
                .damageType(DamageType.PHYSICAL)
                .build();

        WeaponType wand = WeaponType.builder()
                .id(UUID.fromString("97002bc4-474d-4559-b556-72ba0246eb27"))
                .name("Wand")
                .speed(3.0f)
                .isTwoHanded(false)
                .damageType(DamageType.MAGICAL)
                .build();

        WeaponType dagger = WeaponType.builder()
                .id(UUID.fromString("6ddf3bcb-c004-48ab-9a58-747aaecbfa03"))
                .name("Dagger")
                .speed(6.0f)
                .isTwoHanded(false)
                .damageType(DamageType.PHYSICAL)
                .build();

        WeaponType bow = WeaponType.builder()
                .id(UUID.fromString("cdfbd2ad-7c1e-48d7-9f91-2d6d0c089b60"))
                .name("Bow")
                .speed(4.0f)
                .isTwoHanded(true)
                .damageType(DamageType.PHYSICAL)
                .build();



        Weapon twoHandedSword1 = Weapon.builder()
                .id(UUID.fromString("ae52c4e1-86ff-4cb9-909c-eea125c10a5f"))
                .name("Sword of Tears")
                .damage(54)
                .weight(23)
                .value(764)
                .weaponType(twoHandedSword)
                .player(warrior)
                .build();

        Weapon wand1 = Weapon.builder()
                .id(UUID.fromString("a1e77f04-e157-470e-b775-097eb45f4139"))
                .name("Wand of Power")
                .damage(120)
                .weight(2)
                .value(1500)
                .weaponType(wand)
                .player(mage)
                .build();

        Weapon dagger1 = Weapon.builder()
                .id(UUID.fromString("8e4bf5f0-91fa-442c-ab86-eb9901cf5362"))
                .name("Dagger of Dreams")
                .damage(60)
                .weight(1)
                .value(928)
                .weaponType(dagger)
                .player(assassin)
                .build();

        Weapon bow1 = Weapon.builder()
                .id(UUID.fromString("eb8438cf-04dc-46d4-b80e-f10ac21e74ec"))
                .name("Bow of The Hunt")
                .damage(90)
                .weight(5)
                .value(1300)
                .weaponType(bow)
                .player(archer)
                .build();


        if (playerService.find("warrior@game.com").isEmpty()) {

            try {
                playerService.create(warrior);
                playerService.create(mage);
                playerService.create(admin);
                playerService.create(assassin);
                playerService.create(archer);

                weaponTypeService.create(twoHandedSword);
                weaponTypeService.create(wand);
                weaponTypeService.create(dagger);
                weaponTypeService.create(bow);

                weaponService.create(twoHandedSword1);
                weaponService.create(dagger1);
                weaponService.create(bow1);
                weaponService.create(wand1);
            } catch (Exception e) {
                System.out.println("EXCEPTION WHEN INITIALIZING - DATA ALREADY EXIST IN THE DATABASE");
                System.out.println(e.getMessage());
            }
        }

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
