package com.demo.rest.modules.weapon.view;

import com.demo.rest.helpers.ModelFunctionFactory;
import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapon.model.WeaponEditModel;
import com.demo.rest.modules.weapon.service.WeaponService;
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
public class WeaponEdit implements Serializable {


    private final WeaponService service;

    private final ModelFunctionFactory factory;


    @Setter
    @Getter
    private UUID id;


    @Getter
    private WeaponEditModel weapon;



    @Inject
    public WeaponEdit(WeaponService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view.
     */
    public void init() throws IOException {
        Optional<Weapon> weapon = service.find(id);
        if (weapon.isPresent()) {
            this.weapon = factory.weaponToEditModel().apply(weapon.get());
        } else {
            FacesContext.getCurrentInstance().getExternalContext().responseSendError(HttpServletResponse.SC_NOT_FOUND, "weapon not found");
        }
    }

    public String saveAction() {
        service.update(factory.updateWeapon().apply(service.find(id).orElseThrow(), weapon));
        String viewId = FacesContext.getCurrentInstance().getViewRoot().getViewId();
        return viewId + "?faces-redirect=true&includeViewParams=true";
    }

}
