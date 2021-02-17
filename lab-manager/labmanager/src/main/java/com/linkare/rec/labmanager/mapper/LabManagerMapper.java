package com.linkare.rec.labmanager.mapper;

import com.linkare.rec.labmanager.command.LabManagerDto;
import com.linkare.rec.labmanager.persistence.entities.LabManager;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "cdi")
public interface LabManagerMapper {

    //LabManager INSTANCE = Mappers.getMapper(LabManager.class);
    //LabManagerDto DTO_INSTANCE = Mappers.getMapper(LabManagerDto.class);

    @Mappings({
            @Mapping(target="id", source="labManager.id"),
            @Mapping(target="name", source="labManager.name"),
            @Mapping(target = "description", source = "labManager.description"),
            @Mapping(target = "picture", source = "labManager.picture"),
            @Mapping(target = "coordinates", source = "labManager.coordinates"),
            @Mapping(target = "editUsers", source = "labManager.editUsers"),
            @Mapping(target = "url", source = "labManager.url")
    })
    LabManagerDto toLabManagerDto(LabManager labManager);

    @Mappings({
            @Mapping(target="id", source="labManagerDto.id"),
            @Mapping(target="name", source="labManagerDto.name"),
            @Mapping(target = "description", source = "labManagerDto.description"),
            @Mapping(target = "picture", source = "labManagerDto.picture"),
            @Mapping(target = "coordinates", source = "labManagerDto.coordinates"),
            @Mapping(target = "editUsers", source = "labManagerDto.editUsers"),
            @Mapping(target = "url", source = "labManagerDto.url")
    })
    LabManager toLabManager(LabManagerDto labManagerDto);
}
