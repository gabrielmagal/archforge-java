package br.com.archforge_java.service.interfaces;

import br.com.archforge_java.repository.model.UserEntity;
import br.com.archforge_java.service.model.Filter;
import br.com.archforge_java.repository.model.BaseEntity;

import javax.management.relation.InvalidRoleValueException;
import java.util.List;
import java.util.UUID;

public interface ICrudWithUserService<T extends BaseEntity> {
    T findById(UUID id);
    List<T> findAll();
    T save(T entity);
    T update(T entity);
    void delete(UUID id);
    List<T> findAllPaged(int page, int size);
    List<T> filteredFindPaged(Filter filter, int page, int size) throws InvalidRoleValueException;
    long countFiltered(Filter filter);
    String getTenant();
    UserEntity getCurrentLoggedUser();
}
