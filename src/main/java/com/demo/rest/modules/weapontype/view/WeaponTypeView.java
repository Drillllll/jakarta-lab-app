package com.demo.rest.modules.weapontype.view;

import com.demo.rest.helpers.ModelFunctionFactory;
import com.demo.rest.modules.weapon.model.WeaponsModel;
import com.demo.rest.modules.weapon.service.WeaponService;
import com.demo.rest.modules.weapontype.entity.WeaponType;
import com.demo.rest.modules.weapontype.model.WeaponTypeModel;
import com.demo.rest.modules.weapontype.service.WeaponTypeService;
import jakarta.ejb.EJB;
import jakarta.faces.context.FacesContext;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.Setter;


import java.io.IOException;
import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;


@ViewScoped
@Named
public class WeaponTypeView implements Serializable {

    private WeaponTypeService service;
    private WeaponService weaponService;
    private final ModelFunctionFactory factory;


    @Setter
    @Getter
    private UUID id;


    @Getter
    private WeaponTypeModel weaponType;

    @Getter
    private WeaponsModel weapons;


    @Inject
    public WeaponTypeView( ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setWeaponService(WeaponService service) {
        this.weaponService = service;
    }


    @EJB
    public void setWeaponTypeService(WeaponTypeService service) {
        this.service = service;
    }



    public void init() throws IOException {
        Optional<WeaponType> weaponType = service.find(id);
        if (weaponType.isPresent()) {
            this.weaponType = factory.weaponTypeToModel().apply(weaponType.get());
            this.weapons = factory.weaponsToModel().apply(weaponService.findAllByWeaponType(id).get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "weaponType not found");
        }
    }

    public String deleteWeapon(WeaponsModel.Weapon weapon) {
        weaponService.delete(weapon.getId());
        return "weapontype_view?faces-redirect=true&id=" + this.id;
    }

}
