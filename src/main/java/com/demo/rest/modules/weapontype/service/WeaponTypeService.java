package com.demo.rest.modules.weapontype.service;

import com.demo.rest.modules.weapontype.entity.WeaponType;
import com.demo.rest.modules.weapontype.repository.api.WeaponTypeRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import jakarta.transaction.Transactional;

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

        /*Optional<WeaponType> weaponType = repository.find(id);
        *//* Until lazy loaded list of characters is not accessed it is not in cache, so it does not need bo te cared of. *//*
//        profession.ifPresent(value -> log.info("Number of professions: %d".formatted(value.getCharacters().size())));
        return weaponType;*/
    }

    public List<WeaponType> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void create(WeaponType weaponType) {
        repository.create(weaponType);
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }

    @Transactional
    public void update(WeaponType weaponType) {
        repository.update(weaponType);
    }

}
