package br.com.archforge_java.service;

import br.com.archforge_java.repository.model.UserEntity;
import br.com.archforge_java.service.interfaces.ICrudWithUserService;
import br.com.archforge_java.service.interfaces.KeycloakService;
import br.com.archforge_java.service.model.Filter;
import br.com.archforge_java.repository.dao.interfaces.IGenericDao;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import br.com.archforge_java.repository.model.BaseEntity;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.SecurityContext;

import java.util.List;
import java.util.UUID;

@Transactional
public abstract class AbstractCrudWithUserWithUserService<T extends BaseEntity> extends AbstractEntityDescriptionService implements ICrudWithUserService<T> {
    @Inject
    protected IGenericDao dao;

    @Inject
    KeycloakService userService;

    @Context
    public HttpHeaders headers;

    @Context
    public SecurityContext securityContext;

    protected final Class<T> entityClass;

    public AbstractCrudWithUserWithUserService(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T findById(UUID id) {
        return dao.findById(getTenant(), id, entityClass);
    }

    @Override
    public List<T> findAll() {
        return dao.listAll(getTenant(), entityClass);
    }

    @Override
    public List<T> findAllPaged(int page, int size) {
        return dao.findAllPaged(getTenant(), page, size, entityClass);
    }

    @Transactional
    @Override
    public T save(T entity) {
        String tenant = getTenant();
        T saved = dao.update(tenant, entity);
        return dao.findById(tenant, saved.getId(), entityClass);
    }

    @Transactional
    @Override
    public T update(T entity) {
        String tenant = getTenant();
        T saved = dao.update(tenant, entity);
        return dao.findById(tenant, saved.getId(), entityClass);
    }

    @Transactional
    @Override
    public void delete(UUID id) {
        dao.delete(getTenant(), id, entityClass);
    }

    @Override
    public List<T> filteredFindPaged(Filter filter, int page, int size) {
        return dao.filteredFindPaged(getTenant(), filter, page, size, entityClass);
    }

    @Override
    public long countFiltered(Filter filter) {
        return dao.countFiltered(getTenant(), filter, entityClass);
    }

    @Override
    public String getTenant() {
        return headers.getHeaderString("Tenant");
    }

    @Override
    public UserEntity getCurrentLoggedUser() {
        return userService.findByKeycloakId(getTenant(), securityContext.getUserPrincipal().getName());
    }
}
