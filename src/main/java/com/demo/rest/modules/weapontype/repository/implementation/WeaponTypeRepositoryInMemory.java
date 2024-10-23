package com.demo.rest.modules.weapontype.repository.implementation;

import com.demo.rest.datastore.DataStore;
import com.demo.rest.modules.weapontype.entity.WeaponType;
import com.demo.rest.modules.weapontype.repository.api.WeaponTypeRepository;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestScoped
public class WeaponTypeRepositoryInMemory implements WeaponTypeRepository {

    private final DataStore store;

    @Inject
    public WeaponTypeRepositoryInMemory(DataStore store) {
        this.store = store;
    }

    @Override
    public Optional<WeaponType> find(UUID id) {
        return store.findAllWeaponTypes().stream()
                .filter(weaponType -> weaponType.getId().equals(id))
                .findFirst();
    }

    @Override
    public List<WeaponType> findAll() {
        return store.findAllWeaponTypes();
    }

    @Override
    public void create(WeaponType entity) {
        store.createWeaponType(entity);
    }

    @Override
    public void delete(WeaponType entity) {
        store.deleteWeaponType(entity.getId());
    }

    @Override
    public void update(WeaponType entity) {
        store.updateWeaponType(entity);
    }
}