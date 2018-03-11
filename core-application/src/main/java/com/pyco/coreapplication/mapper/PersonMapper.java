package com.pyco.coreapplication.mapper;

import com.pyco.coreapplication.domain.Person;
import com.pyco.coreapplication.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PersonMapper {

    public PersonMapper INSTANCE = Mappers.getMapper(PersonMapper.class);

    @Mapping(target = "password", ignore = true)
    UserDto toUserDto(Person person);

}
