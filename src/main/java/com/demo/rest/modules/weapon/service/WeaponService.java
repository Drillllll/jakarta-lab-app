package com.demo.rest.modules.weapon.service;

import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapon.repository.api.WeaponRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class WeaponService {

    private final WeaponRepository repository;

    @Inject
    public WeaponService(WeaponRepository repository) {
        this.repository = repository;
    }

    public Optional<Weapon> find(UUID id) {
        return repository.find(id);
    }

    public List<Weapon> findAll() {
        return repository.findAll();
    }

    public void create(Weapon weapon) {

        repository.create(weapon);
    }

    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }

    public void update(Weapon weapon) {
        repository.update(weapon);
    }

}
