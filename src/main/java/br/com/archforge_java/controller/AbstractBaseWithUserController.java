package br.com.archforge_java.controller;

import br.com.archforge_java.mapper.BaseMapper;
import br.com.archforge_java.service.AbstractEntityDescriptionService;
import br.com.archforge_java.service.model.Filter;
import br.com.archforge_java.service.model.FilterParam;
import br.com.archforge_java.controller.dto.BaseDto;
import jakarta.ws.rs.*;
import br.com.archforge_java.repository.model.BaseEntity;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import br.com.archforge_java.service.AbstractCrudWithUserWithUserService;

import javax.management.relation.InvalidRoleValueException;
import java.util.*;

public abstract class AbstractBaseWithUserController<T extends BaseDto, E extends BaseEntity> extends AbstractEntityDescriptionService {

    protected final AbstractCrudWithUserWithUserService<E> service;
    protected final BaseMapper<T, E> mapper;

    public AbstractBaseWithUserController(AbstractCrudWithUserWithUserService<E> service, BaseMapper<T, E> mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @POST
    public T save(@RequestBody T dto) {
        E entity = mapper.toEntity(dto);
        E savedEntity = service.save(entity);
        if (savedEntity != null) {
            return mapper.toDto(savedEntity);
        } else {
            throw new RuntimeException("Erro ao salvar.");
        }
    }

    @PUT
    @Path("/{id}")
    public T update(@PathParam("id") UUID id, @RequestBody T dto) {
        E existingEntity = service.findById(id);
        if (existingEntity != null) {
            E entityToUpdate = mapper.toEntity(dto);
            E updatedEntity = service.update(entityToUpdate);
            if (updatedEntity != null) {
                return mapper.toDto(updatedEntity);
            } else {
                throw new RuntimeException("Erro ao atualizar.");
            }
        } else {
            throw new NotFoundException();
        }
    }

    @GET
    @Path("/{id}")
    public T findById(@PathParam("id") UUID id) {
        E entity = service.findById(id);
        if (entity != null) {
            return mapper.toDto(entity);
        } else {
            throw new NotFoundException();
        }
    }

    @GET
    public Map<String, Object> findAllPaged(@Context UriInfo uriInfo,
                                            @QueryParam("page") @DefaultValue("0") int page,
                                            @QueryParam("size") @DefaultValue("10") int size) throws InvalidRoleValueException {
        Filter filter = new Filter();
        List<FilterParam> filterParams = new ArrayList<>();

        for (Map.Entry<String, List<String>> entry : uriInfo.getQueryParameters().entrySet()) {
            String key = entry.getKey();
            if (!Set.of("page", "size").contains(key)) {
                filterParams.add(new FilterParam(key, entry.getValue().getFirst()));
            }
        }

        filter.setFilterParams(filterParams);
        filter.setSortingParams(Collections.emptyList());

        List<E> entities = service.filteredFindPaged(filter, page, size);
        List<T> dtos = mapper.toDtoList(entities);
        long total = service.countFiltered(filter);

        Map<String, Object> response = new HashMap<>();
        response.put("content", dtos);
        response.put("totalElements", total);
        response.put("page", page);
        response.put("size", size);

        return response;
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") UUID id) {
        E existingEntity = service.findById(id);
        if (existingEntity != null) {
            service.delete(id);
        } else {
            throw new NotFoundException();
        }
    }
}
