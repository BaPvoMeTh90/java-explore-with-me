package ru.practicum.mapper;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.practicum.dto.EndpointHitDto;
import ru.practicum.model.EndpointHit;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class EndpointHitMapper {

    public static EndpointHit toEntity(EndpointHitDto dto) {
        EndpointHit entity = new EndpointHit();
        entity.setApp(dto.getApp());
        entity.setUri(dto.getUri());
        entity.setIp(dto.getIp());
        entity.setTimestamp(dto.getTimestamp());
        return entity;
    };

    public static EndpointHitDto toDto(EndpointHit entity) {
        EndpointHitDto dto = new EndpointHitDto();
        dto.setApp(entity.getApp());
        dto.setUri(entity.getUri());
        dto.setIp(entity.getIp());
        dto.setTimestamp(entity.getTimestamp());
        return dto;
    };
}
