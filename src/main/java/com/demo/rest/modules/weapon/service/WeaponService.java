package com.demo.rest.modules.weapon.service;

import com.demo.rest.modules.player.repository.api.PlayerRepository;
import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapon.repository.api.WeaponRepository;
import com.demo.rest.modules.weapontype.repository.api.WeaponTypeRepository;
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


    @Inject
    public WeaponService(
            WeaponRepository weaponRepository,
            PlayerRepository playerRepository,
            WeaponTypeRepository weaponTypeRepository
    ) {
        this.weaponRepository = weaponRepository;
        this.playerRepository = playerRepository;
        this.weaponTypeRepository = weaponTypeRepository;

    }

    public Optional<Weapon> find(UUID id) {
        return weaponRepository.find(id);
    }

    public List<Weapon> findAll() {
        return weaponRepository.findAll();
    }

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

    }

    public void delete(UUID id) {
        weaponRepository.delete(weaponRepository.find(id).orElseThrow());
    }

    public void update(Weapon weapon) {
        weaponRepository.update(weapon);
    }

    public  Optional<List<Weapon>> findAllByPlayer(UUID id) {
        return playerRepository.find(id)
                .map(weaponRepository::findAllByPlayer);
    }

    public  Optional<List<Weapon>> findAllByWeaponType(UUID id) {
        return weaponTypeRepository.find(id)
                .map(weaponRepository::findAllByWeaponType);
    }
}
