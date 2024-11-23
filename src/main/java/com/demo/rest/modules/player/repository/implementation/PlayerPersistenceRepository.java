package com.demo.rest.modules.player.repository.implementation;

import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.player.repository.api.PlayerRepository;
import jakarta.enterprise.context.Dependent;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Repository for User entity. Repositories should be used in business layer (e.g.: in services). The request scope is a
 * result of the fact that {@link EntityManager} objects cannot be used in multiple threads (are not thread safe).
 * Because services are CDI application scoped beans (technically singletons) then repositories must be thread scoped in
 * order to ensure single entity manager for single thread.
 */
@Dependent
public class PlayerPersistenceRepository implements PlayerRepository {

    /**
     * Connection with the database (not thread safe).
     */
    private EntityManager em;

    @PersistenceContext
    public void setEm(EntityManager em) {
        this.em = em;
    }

    @Override
    public Optional<Player> find(UUID id) {
        return Optional.ofNullable(em.find(Player.class, id));
    }

    @Override
    public List<Player> findAll() {
        return em.createQuery("select p from Player p", Player.class).getResultList();
    }

    @Override
    public void create(Player entity) {
        em.persist(entity);
    }

    @Override
    public void delete(Player entity) {
        em.remove(em.find(Player.class, entity.getId()));
    }

    @Override
    public void update(Player entity) {
        em.merge(entity);
    }

    @Override
    public Optional<Player> findByLogin(String login) {
        try {
            return Optional.of(em.createQuery("select p from Player p where p.login = :login", Player.class)
                    .setParameter("login", login)
                    .getSingleResult());
        } catch (NoResultException ex) {
            return Optional.empty();
        }
    }

}
