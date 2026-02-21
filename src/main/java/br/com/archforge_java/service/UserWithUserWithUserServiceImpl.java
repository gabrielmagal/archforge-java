package br.com.archforge_java.service;

import br.com.archforge_java.repository.model.UserEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserWithUserWithUserServiceImpl extends AbstractCrudWithUserWithUserService<UserEntity> {
    public UserWithUserWithUserServiceImpl() {
        super(UserEntity.class);
    }
}
