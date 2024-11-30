package com.demo.rest.modules.weapon.view;

import com.demo.rest.helpers.ModelFunctionFactory;
import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapon.model.WeaponModel;
import com.demo.rest.modules.weapon.service.WeaponService;
import jakarta.ejb.EJBAccessException;
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
public class WeaponView implements Serializable {

    private final WeaponService weaponService;
    private final ModelFunctionFactory factory;

    @Setter
    @Getter
    private UUID id;

    @Getter
    private WeaponModel weapon;


    @Inject
    public WeaponView(ModelFunctionFactory factory, WeaponService weaponService) {
        this.factory = factory;
        this.weaponService = weaponService;
    }


    public void init() throws IOException {
        try {
            Optional<Weapon> weapon = weaponService.find(id);
            if (weapon.isPresent()) {
                this.weapon = factory.weaponToModel().apply(weapon.get());
            } else {
                FacesContext.getCurrentInstance().getExternalContext()
                        .responseSendError(HttpServletResponse.SC_NOT_FOUND, "Weapon not found");
            }
        } catch (EJBAccessException e) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.getExternalContext().responseSendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
        }
    }


}
