package com.example.demo.converter;

import java.util.List;

public interface DTOEntityConverter<DTO, ENTITY> {
    DTO toDto(ENTITY e);
    ENTITY toEntity(DTO d);

    default List<DTO> listToDTO(List<ENTITY> entities) {
        return entities.stream().map(this::toDto).toList();
    }
    default List<ENTITY> listToEntity(List<DTO> dtos) {
        return dtos.stream().map(this::toEntity).toList();
    }
}