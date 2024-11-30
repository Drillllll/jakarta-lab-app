package com.demo.rest.authorization.interceptor;


import com.demo.rest.authorization.exception.NoPrincipalException;
import com.demo.rest.authorization.exception.NoRolesException;
import com.demo.rest.authorization.interceptor.biding.AllowAdminOrOwner;
import com.demo.rest.modules.player.entity.PlayerRoles;
import com.demo.rest.modules.weapon.entity.Weapon;
import com.demo.rest.modules.weapon.service.WeaponService;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;


import java.util.Optional;
import java.util.UUID;

/**
 * Binding for interceptor allowing only admin or user owning the weapon.
 */
@Interceptor
@AllowAdminOrOwner
@Priority(10)
public class AllowAdminOrOwnerInterceptor {


    private final SecurityContext securityContext;


    private final WeaponService weaponService;


    @Inject
    public AllowAdminOrOwnerInterceptor(SecurityContext securityContext, WeaponService weaponService) {
        this.securityContext = securityContext;
        this.weaponService = weaponService;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        if (securityContext.getCallerPrincipal() == null) {
            throw new NoPrincipalException();
        }
        if (authorized(context)) {
            return context.proceed();
        }
        throw new NoRolesException();
    }

    /**
     * @param context invocation context
     * @return true if caller principal is authorized to edit weapon represented by first method parameter
     */
    private boolean authorized(InvocationContext context) {
        if (securityContext.isCallerInRole(PlayerRoles.ADMIN)) {
            return true;
        } else if (securityContext.isCallerInRole(PlayerRoles.PLAYER)) {
            Object provided = context.getParameters()[0];//Simple assumption that first parameter will be weapon or uuid
            Optional<Weapon> weapon;
            if (provided instanceof Weapon) {
                weapon = weaponService.find(((Weapon) provided).getId());
            } else if (provided instanceof UUID) {
                weapon = weaponService.find((UUID) provided);
            } else {
                throw new IllegalStateException("No weapon of UUID as first method parameter.");
            }

            return weapon.isPresent()
                    && weapon.get().getPlayer().getLogin().equals(securityContext.getCallerPrincipal().getName());
        }
        return false;
    }

}
