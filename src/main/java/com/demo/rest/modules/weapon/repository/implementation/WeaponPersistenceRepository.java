package com.demo.rest.modules.weapon.repository.implementation;

import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapon.repository.api.WeaponRepository;
import com.demo.rest.modules.weapontype.entity.WeaponType;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Dependent
public class WeaponPersistenceRepository implements WeaponRepository {

    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Weapon> find(UUID id) {
        return Optional.ofNullable(em.find(Weapon.class, id));
    }

    @Override
    public List<Weapon> findAll() {
        return em.createQuery("select w from Weapon w", Weapon.class).getResultList();
    }

    @Override
    public void create(Weapon entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Weapon entity) {
        em.remove(em.find(Weapon.class, entity.getId()));
    }

    @Override
    public void update(Weapon entity) {
        em.merge(entity);
    }

    @Override
    public List<Weapon> findAllByWeaponType(WeaponType weaponType) {
        return em.createQuery("select w from Weapon w where w.weaponType = :weaponType", Weapon.class)
                .setParameter("weaponType", weaponType)
                .getResultList();
    }

    @Override
    public List<Weapon> findAllByPlayer(Player player) {
        return em.createQuery("select w from Weapon w where w.player = :player", Weapon.class)
                .setParameter("player", player)
                .getResultList();
    }

}
