package com.demo.rest.modules.weapon.service;

import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.player.entity.PlayerRoles;
import com.demo.rest.modules.player.repository.api.PlayerRepository;
import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapon.repository.api.WeaponRepository;
import com.demo.rest.modules.weapontype.repository.api.WeaponTypeRepository;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.EJBAccessException;
import jakarta.security.enterprise.SecurityContext;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import jakarta.ejb.LocalBean;
import jakarta.ejb.Stateless;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@LocalBean
@Stateless
@NoArgsConstructor(force = true)
public class WeaponService {

    private final WeaponRepository weaponRepository;
    private final PlayerRepository playerRepository;
    private final WeaponTypeRepository weaponTypeRepository;

    private final SecurityContext securityContext;



    @Inject
    public WeaponService(
            WeaponRepository weaponRepository,
            PlayerRepository playerRepository,
            WeaponTypeRepository weaponTypeRepository,
            @SuppressWarnings("CdiInjectionPointsInspection") SecurityContext securityContext
    ) {
        this.weaponRepository = weaponRepository;
        this.playerRepository = playerRepository;
        this.weaponTypeRepository = weaponTypeRepository;
        this.securityContext = securityContext;

    }

    @RolesAllowed(PlayerRoles.PLAYER)
    public Optional<Weapon> find(UUID id) {
        Optional<Weapon> optWeapon =  weaponRepository.find(id);
        checkAdminRoleOrOwner(optWeapon);
        return optWeapon;
    }

    @RolesAllowed(PlayerRoles.PLAYER)
    public Optional<Weapon> find(Player player, UUID id) {
        return weaponRepository.findByIdAndPLayer(id, player);
    }

    @RolesAllowed(PlayerRoles.PLAYER)
    public Optional<Weapon> findForCallerPrincipal(UUID id) {
        if (securityContext.isCallerInRole(PlayerRoles.ADMIN)) {
            return find(id);
        }
        Player player = playerRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return find(player, id);
    }

    @RolesAllowed(PlayerRoles.PLAYER)
    public List<Weapon> findAll() {
        return findAllForCallerPrincipal();
        //return weaponRepository.findAll();
    }


    @RolesAllowed(PlayerRoles.PLAYER)
    public List<Weapon> findAll(Player player) {
        return weaponRepository.findAllByPlayer(player);
    }

    @RolesAllowed(PlayerRoles.PLAYER)
    public List<Weapon> findAllForCallerPrincipal() {
        if (securityContext.isCallerInRole(PlayerRoles.ADMIN)) {
            //return findAll();
            return weaponRepository.findAll();
        }
        Player player = playerRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);
        return findAll(player);
    }


    @RolesAllowed(PlayerRoles.ADMIN)
    public void create(Weapon weapon) {
        if (weaponRepository.find(weapon.getId()).isPresent()) {
            throw new IllegalArgumentException("Weapon already exists.");
        }
        if (weaponTypeRepository.find(weapon.getWeaponType().getId()).isEmpty()) {
            throw new IllegalArgumentException("weaponType does not exists.");
        }
        if (playerRepository.find(weapon.getPlayer().getId()).isEmpty()) {
            throw new IllegalArgumentException("player does not exists.");
        }
        weaponRepository.create(weapon);
        /* Both sides of relationship must be handled (if accessed) because of cache. */
        weaponTypeRepository.find(weapon.getWeaponType().getId())
                .ifPresent(weaponType -> weaponType.getWeapons().add(weapon));
        playerRepository.find(weapon.getPlayer().getId())
                .ifPresent(player -> player.getWeapons().add(weapon));


        /*LoggerFactory.getLogger(WeaponService.class).info("User: {} - Operation: CREATE - Weapon ID: {}",
                securityContext.getCallerPrincipal().getName(), weapon.getId());*/
    }

    @RolesAllowed(PlayerRoles.PLAYER)
    public void createForCallerPrincipal(Weapon weapon) {
        Player player = playerRepository.findByLogin(securityContext.getCallerPrincipal().getName())
                .orElseThrow(IllegalStateException::new);

        weapon.setPlayer(player);
        create(weapon);
    }

    @RolesAllowed(PlayerRoles.PLAYER)
    public void delete(UUID id) {
        checkAdminRoleOrOwner(weaponRepository.find(id));
        weaponRepository.delete(weaponRepository.find(id).orElseThrow());

        //log.info("User: {} - Operation: DELETE - Weapon ID: {}",
                //securityContext.getCallerPrincipal().getName(), id);
    }

    @RolesAllowed(PlayerRoles.PLAYER)
    public void update(Weapon weapon) {
        checkAdminRoleOrOwner(weaponRepository.find(weapon.getId()));
        weaponRepository.update(weapon);

        //log.info("User: {} - Operation: UPDATE - Weapon ID: {}",
                //securityContext.getCallerPrincipal().getName(), weapon.getId());
    }


    @RolesAllowed(PlayerRoles.ADMIN)
    public  Optional<List<Weapon>> findAllByPlayer(UUID id) {
        return playerRepository.find(id)
                .map(weaponRepository::findAllByPlayer);
    }

    /*@RolesAllowed(PlayerRoles.PLAYER)
    public  Optional<List<Weapon>> findAllByWeaponType(UUID id) {
        return weaponTypeRepository.find(id)
                .map(weaponRepository::findAllByWeaponType);
    }*/

    @RolesAllowed(PlayerRoles.PLAYER)
    public  Optional<List<Weapon>> findAllByWeaponType(UUID id) {

        if(securityContext.isCallerInRole(PlayerRoles.ADMIN)) {
            return weaponTypeRepository.find(id)
                    .map(weaponRepository::findAllByWeaponType);
        }
        return weaponTypeRepository.find(id)
                .map(weaponType -> weaponRepository.findAllByWeaponTypeAndPlayer(weaponType, playerRepository.findByLogin(securityContext.getCallerPrincipal().getName()).get()));
    }

    private void checkAdminRoleOrOwner(Optional<Weapon> weapon) throws EJBAccessException {
        if (securityContext.isCallerInRole(PlayerRoles.ADMIN)) {
            return;
        }
        if (securityContext.isCallerInRole(PlayerRoles.PLAYER)
                && weapon.isPresent()
                && weapon.get().getPlayer().getLogin().equals(securityContext.getCallerPrincipal().getName())) {
            return;
        }
        throw new EJBAccessException("Caller not authorized.");
    }


}
