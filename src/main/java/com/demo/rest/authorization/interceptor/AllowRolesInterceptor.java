package com.demo.rest.authorization.interceptor;

import com.demo.rest.authorization.exception.NoPrincipalException;
import com.demo.rest.authorization.exception.NoRolesException;
import com.demo.rest.authorization.interceptor.biding.AllowRoles;
import jakarta.annotation.Priority;
import jakarta.inject.Inject;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.security.enterprise.SecurityContext;

/**
 * Allows any of specified roles.
 */
@Interceptor
@AllowRoles
@Priority(10)
public class AllowRolesInterceptor {

    /**
     * Security context.
     */
    private final SecurityContext securityContext;

    /**
     * @param securityContext security context
     */
    @Inject
    public AllowRolesInterceptor(SecurityContext securityContext) {
        this.securityContext = securityContext;
    }

    @AroundInvoke
    public Object invoke(InvocationContext context) throws Exception {
        if (securityContext.getCallerPrincipal() == null) {
            throw new NoPrincipalException();
        }
        String[] roles = context.getMethod().getAnnotation(AllowRoles.class).value();
        for (String role : roles) {
            if (securityContext.isCallerInRole(role)) {
                return context.proceed();
            }
        }
        throw new NoRolesException();
    }

}
