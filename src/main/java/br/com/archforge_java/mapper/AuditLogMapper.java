package br.com.archforge_java.mapper;

import br.com.archforge_java.controller.dto.AuditLogDto;
import br.com.archforge_java.repository.model.AuditLogEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "jakarta")
public interface AuditLogMapper extends BaseMapper<AuditLogDto, AuditLogEntity> {
    AuditLogDto toDto(AuditLogEntity entity);
    AuditLogEntity toEntity(AuditLogDto dto);
}