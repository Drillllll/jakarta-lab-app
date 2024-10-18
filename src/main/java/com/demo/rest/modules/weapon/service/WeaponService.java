package com.demo.rest.modules.weapon.service;

import com.demo.rest.modules.player.repository.api.PlayerRepository;
import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapon.repository.api.WeaponRepository;
import com.demo.rest.modules.weapontype.repository.api.WeaponTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class WeaponService {

    private final WeaponRepository weaponRepository;
    private final PlayerRepository playerRepository;
    private final WeaponTypeRepository weaponTypeRepository;


    @Inject
    public WeaponService(WeaponRepository weaponRepository, PlayerRepository playerRepository, WeaponTypeRepository weaponTypeRepository) {
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

        weaponRepository.create(weapon);
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
