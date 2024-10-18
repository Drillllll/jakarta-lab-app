package com.demo.rest.modules.weapon.repository.api;

import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapontype.entity.WeaponType;
import com.demo.rest.repository.api.Repository;

import java.util.List;
import java.util.UUID;

public interface WeaponRepository extends Repository<Weapon, UUID> {

    List<Weapon> findAllByPlayer(Player player);

    List<Weapon> findAllByWeaponType(WeaponType weaponType);

}
