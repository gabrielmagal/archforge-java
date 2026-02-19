package br.com.archforge_java.infrastructure.security;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.SecurityContext;

@ApplicationScoped
public class AuthenticatedUserProviderImpl implements AuthenticatedUserProvider {
    @Inject
    SecurityContext securityContext;

    @Override
    public String getAuthenticatedUserId() {
        try {
            if (securityContext != null && securityContext.getUserPrincipal() != null) {
                return securityContext.getUserPrincipal().getName();
            }
        } catch (IllegalStateException e) {
            return "system";
        }
        return "system";
    }
}
