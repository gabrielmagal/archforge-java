package br.com.archforge_java.service;

import br.com.archforge_java.repository.model.AuditLogEntity;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class AuditLogServiceImpl extends AbstractCrudService<AuditLogEntity> {
    public AuditLogServiceImpl() {
        super(AuditLogEntity.class);
    }
}
