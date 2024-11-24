package com.demo.rest.modules.weapon.view;

import com.demo.rest.helpers.ModelFunctionFactory;
import com.demo.rest.modules.weapon.model.WeaponCreateModel;
import com.demo.rest.modules.weapon.service.WeaponService;
import com.demo.rest.modules.weapontype.model.WeaponTypeModel;
import com.demo.rest.modules.weapontype.service.WeaponTypeService;
import jakarta.enterprise.context.Conversation;
import jakarta.enterprise.context.ConversationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.java.Log;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@ConversationScoped
@Named
@Log
@NoArgsConstructor(force = true)
public class WeaponCreate implements Serializable {


    private final WeaponService weaponService;

    private final WeaponTypeService weaponTypeService;

    private final ModelFunctionFactory factory;


    @Getter
    private WeaponCreateModel weapon;

    @Getter
    private List<WeaponTypeModel> weaponTypes;

    /**
     * Injected conversation.
     */
    private final Conversation conversation;


    @Inject
    public WeaponCreate(
            WeaponService weaponService,
            WeaponTypeService weaponTypeService,
            ModelFunctionFactory factory,
            Conversation conversation
    ) {
        this.weaponService = weaponService;
        this.factory = factory;
        this.weaponTypeService = weaponTypeService;
        this.conversation = conversation;
    }

    /**
     * In order to prevent calling service on different steps of JSF request lifecycle, model property is cached within
     * field and initialized during init of the view. @PostConstruct method is called after h:form header is already
     * rendered. Conversation should be started in f:metadata/f:event.
     */
    public void init() {
        if (conversation.isTransient()) {
            weaponTypes = weaponTypeService.findAll().stream()
                    .map(factory.weaponTypeToModel())
                    .collect(Collectors.toList());
            weapon = WeaponCreateModel.builder()
                    .id(UUID.randomUUID())
                    .build();

            conversation.begin();
        }
    }

    public String cancelAction() {
        conversation.end();
        return "/weapontype/weapontype_list.xhtml?faces-redirect=true";
    }

    public String saveAction() {
        weaponService.createForCallerPrincipal(factory.modelToWeapon().apply(weapon));
        conversation.end();
        return "/weapontype/weapontype_list.xhtml?faces-redirect=true";
    }



}
