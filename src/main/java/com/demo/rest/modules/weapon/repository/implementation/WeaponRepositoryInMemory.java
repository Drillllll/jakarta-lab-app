package com.demo.rest.modules.weapon.repository.implementation;

import com.demo.rest.datastore.DataStore;
import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapon.repository.api.WeaponRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class WeaponRepositoryInMemory implements WeaponRepository {

    private final DataStore store;

    @Inject
    public WeaponRepositoryInMemory(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<Weapon> find(UUID id) {
        return store.findAllWeapons().stream()
                .filter(weapon -> weapon.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<Weapon> findAll() {
        return store.findAllWeapons();
    }

    @Override
    public void create(Weapon entity) {
        store.createWeapon(entity);
    }

    @Override
    public void delete(Weapon entity) {
        throw new UnsupportedOperationException("Not implemented.");
    }

    @Override
    public void update(Weapon entity) {
        store.updateWeapon(entity);
    }

}
