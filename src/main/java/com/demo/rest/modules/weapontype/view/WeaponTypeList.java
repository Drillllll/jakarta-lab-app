package com.demo.rest.modules.weapontype.view;

import com.demo.rest.helpers.ModelFunctionFactory;
import com.demo.rest.modules.weapontype.model.WeaponTypesModel;
import com.demo.rest.modules.weapontype.service.WeaponTypeService;
import jakarta.ejb.EJB;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

@RequestScoped
@Named
public class WeaponTypeList {

    private WeaponTypeService service;

    private WeaponTypesModel weaponTypes;

    private final ModelFunctionFactory factory;

    @Inject
    public WeaponTypeList(ModelFunctionFactory factory) {
        this.factory = factory;
    }

    @EJB
    public void setService(WeaponTypeService service) {
        this.service = service;
    }


    public WeaponTypesModel getWeaponTypes() {
        if (weaponTypes == null) {
            weaponTypes = factory.weaponTypesToModel().apply(service.findAll());
        }
        return weaponTypes;
    }

    public String deleteAction(WeaponTypesModel.WeaponType weaponType) {
        service.delete(weaponType.getId());
        return "weapontype_list?faces-redirect=true";
    }
}
