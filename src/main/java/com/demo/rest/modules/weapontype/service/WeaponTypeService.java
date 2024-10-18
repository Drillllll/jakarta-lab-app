package com.demo.rest.modules.weapontype.service;

import com.demo.rest.modules.weapontype.entity.WeaponType;
import com.demo.rest.modules.weapontype.repository.api.WeaponTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class WeaponTypeService {

    private final WeaponTypeRepository repository;

    @Inject
    public WeaponTypeService(WeaponTypeRepository repository) {
        this.repository = repository;
    }

    public Optional<WeaponType> find(UUID id) {
        return repository.find(id);
    }

    public List<WeaponType> findAll() {
        return repository.findAll();
    }

    public void create(WeaponType weaponType) {
        repository.create(weaponType);
    }

    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }

    public void update(WeaponType weaponType) {
        repository.update(weaponType);
    }

}
