package com.demo.rest.modules.weapontype.repository.implementation;

import jakarta.enterprise.context.Dependent;
import com.demo.rest.modules.weapontype.entity.WeaponType;
import com.demo.rest.modules.weapontype.repository.api.WeaponTypeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Dependent
public class WeaponTypePersistenceRepository implements WeaponTypeRepository {

    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<WeaponType> find(UUID id) {
        return Optional.ofNullable(em.find(WeaponType.class, id));
    }

    @Override
    public List<WeaponType> findAll() {
        return em.createQuery("select w from WeaponType w", WeaponType.class).getResultList();
    }

    @Override
    public void create(WeaponType entity) {
        em.persist(entity);
    }

    @Override
    public void delete(WeaponType entity) {
        em.remove(em.find(WeaponType.class, entity.getId()));
    }

    @Override
    public void update(WeaponType entity) {
        em.merge(entity);
    }

}
