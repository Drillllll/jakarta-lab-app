package com.demo.rest.modules.weapontype.model.converter;


import com.demo.rest.helpers.ModelFunctionFactory;
import com.demo.rest.modules.weapontype.entity.WeaponType;
import com.demo.rest.modules.weapontype.model.WeaponTypeModel;
import com.demo.rest.modules.weapontype.service.WeaponTypeService;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.UUID;

/**
 * Faces converter  The managed attribute in {@link @FacesConverter} allows the converter to
 * be the CDI bean. In previous version of JSF converters were always created inside JSF lifecycle and where not managed
 * by container that is injection was not possible. As this bean is not annotated with scope the beans.xml descriptor
 * must be present.
 */
@FacesConverter(forClass = WeaponTypeModel.class, managed = true)
public class WeaponTypeModelConverter implements Converter<WeaponTypeModel> {

    private final WeaponTypeService service;

    private final ModelFunctionFactory factory;


    @Inject
    public WeaponTypeModelConverter(WeaponTypeService service, ModelFunctionFactory factory) {
        this.service = service;
        this.factory = factory;
    }

    @Override
    public WeaponTypeModel getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        Optional<WeaponType> weaponType = service.find(UUID.fromString(value));
        return weaponType.map(factory.weaponTypeToModel()).orElse(null);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, WeaponTypeModel value) {
        return value == null ? "" : value.getId().toString();
    }

}
