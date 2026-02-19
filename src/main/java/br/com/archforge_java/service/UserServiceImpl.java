package br.com.archforge_java.service;

import br.com.archforge_java.repository.model.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserServiceImpl extends AbstractCrudService<UserEntity> {
    public UserServiceImpl() {
        super(UserEntity.class);
    }
}
