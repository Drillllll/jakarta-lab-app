package com.demo.rest.modules.weapon.repository.implementation;

import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapon.entity.Weapon_;
import com.demo.rest.modules.weapon.repository.api.WeaponRepository;
import com.demo.rest.modules.weapontype.entity.WeaponType;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;


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

        //return em.createQuery("select w from Weapon w", Weapon.class).getResultList();

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Weapon> query = cb.createQuery(Weapon.class);
        Root<Weapon> root = query.from(Weapon.class);
        query.select(root);
        return em.createQuery(query).getResultList();

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
        /*return em.createQuery("select w from Weapon w where w.weaponType = :weaponType", Weapon.class)
                .setParameter("weaponType", weaponType)
                .getResultList();*/

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Weapon> query = cb.createQuery(Weapon.class);
        Root<Weapon> root = query.from(Weapon.class);
        query.select(root)
                .where(cb.equal(root.get(Weapon_.weaponType), weaponType));
        return em.createQuery(query).getResultList();
    }

    @Override
    public List<Weapon> findAllByWeaponTypeAndPlayer(WeaponType weaponType, Player player) {
        /*return em.createQuery("select w from Weapon w where w.weaponType = :weaponType and w.player = :player", Weapon.class)
                .setParameter("weaponType", weaponType)
                .setParameter("player", player)
                .getResultList();*/

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Weapon> query = cb.createQuery(Weapon.class);
        Root<Weapon> root = query.from(Weapon.class);
        query.select(root)
                .where(cb.and(
                        cb.equal(root.get(Weapon_.weaponType), weaponType),
                        cb.equal(root.get(Weapon_.player), player)
                ));
        return em.createQuery(query).getResultList();
    }


    @Override
    public Optional<Weapon> findByIdAndPLayer(UUID id, Player player) {
        try {
            /*return Optional.of(em.createQuery("select w from Weapon w where w.id = :id and w.player = :player", Weapon.class)
                    .setParameter("player", player)
                    .setParameter("id", id)
                    .getSingleResult());*/

            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Weapon> query = cb.createQuery(Weapon.class);
            Root<Weapon> root = query.from(Weapon.class);
            query.select(root)
                    .where(cb.and(
                            cb.equal(root.get(Weapon_.player), player),
                            cb.equal(root.get(Weapon_.id), id)
                    ));
            return Optional.of(em.createQuery(query).getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }

    }

    @Override
    public List<Weapon> findAllByPlayer(Player player) {
        /*return em.createQuery("select w from Weapon w where w.player = :player", Weapon.class)
                .setParameter("player", player)
                .getResultList();*/

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Weapon> query = cb.createQuery(Weapon.class);
        Root<Weapon> root = query.from(Weapon.class);
        query.select(root)
                .where(cb.equal(root.get(Weapon_.player), player));
        return em.createQuery(query).getResultList();
    }

}
