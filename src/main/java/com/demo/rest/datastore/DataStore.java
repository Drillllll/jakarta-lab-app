package com.demo.rest.datastore;

import com.demo.rest.helpers.CloningUtility;
import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapontype.entity.WeaponType;
import lombok.NoArgsConstructor;
import jakarta.enterprise.context.ApplicationScoped;
import lombok.extern.java.Log;
import jakarta.inject.Inject;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Log
@ApplicationScoped
@NoArgsConstructor(force = true)
public class DataStore {

    private final CloningUtility cloningUtility;

    private final Set<Player> players = new HashSet<>();
    private final Set<Weapon> weapons = new HashSet<>();
    private final Set<WeaponType> weaponTypes = new HashSet<>();

    @Inject
    public DataStore(CloningUtility cloningUtility) {
        this.cloningUtility = cloningUtility;
    }

    /** player */

    public synchronized List<Player> findAllPlayers() {
        return players.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createPlayer(Player value) throws IllegalArgumentException {
        if (players.stream().anyMatch(player -> player.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The player id \"%s\" is not unique".formatted(value.getId()));
        }
        players.add(cloningUtility.clone(value));
    }

    public synchronized void updatePlayer(Player value) throws IllegalArgumentException {
        if (players.removeIf(player -> player.getId().equals(value.getId()))) {
            players.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The player with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    /** weapon */

    public synchronized List<Weapon> findAllWeapons() {
        return weapons.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createWeapon(Weapon value) throws IllegalArgumentException {
        if (weapons.stream().anyMatch(weapon -> weapon.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The weapon id \"%s\" is not unique".formatted(value.getId()));
        }
        Weapon entity = cloneWithRelationShip(value);
        weapons.add(entity);
    }

    public synchronized void updateWeapon(Weapon value) throws IllegalArgumentException {
        Weapon entity = cloneWithRelationShip(value);
        if (weapons.removeIf(weapon -> weapon.getId().equals(value.getId()))) {
            weapons.add(entity);
        } else {
            throw new IllegalArgumentException("The weapon with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    /** weapon type */

    public synchronized List<WeaponType> findAllWeaponTypes() {
        return weaponTypes.stream()
                .map(cloningUtility::clone)
                .collect(Collectors.toList());
    }

    public synchronized void createWeaponType(WeaponType value) throws IllegalArgumentException {
        if (weaponTypes.stream().anyMatch(weaponType -> weaponType.getId().equals(value.getId()))) {
            throw new IllegalArgumentException("The weapon type id \"%s\" is not unique".formatted(value.getId()));
        }
        weaponTypes.add(cloningUtility.clone(value));
    }

    public synchronized void updateWeaponType(WeaponType value) throws IllegalArgumentException {
        if (weaponTypes.removeIf(weaponType -> weaponType.getId().equals(value.getId()))) {
            weaponTypes.add(cloningUtility.clone(value));
        } else {
            throw new IllegalArgumentException("The weapon type with id \"%s\" does not exist".formatted(value.getId()));
        }
    }

    private Weapon cloneWithRelationShip(Weapon value) {
        Weapon entity = cloningUtility.clone(value);

        if (entity.getPlayer() != null) {
            entity.setPlayer(
                    players.stream()
                            .filter(player -> player.getId().equals(value.getPlayer().getId()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("No player with id \"%s\" found".formatted(value.getPlayer().getId())))
            );
        }

        if (entity.getWeaponType() != null) {
            entity.setWeaponType(
                    weaponTypes.stream()
                            .filter(weaponType -> weaponType.getId().equals(value.getWeaponType().getId()))
                            .findFirst()
                            .orElseThrow(() -> new IllegalArgumentException("No weapon type with id \"%s\" found".formatted(value.getWeaponType().getId())))
            );
        }

        return entity;
    }


}
