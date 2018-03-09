package com.pyco.coreapplication.mapper;

import com.pyco.coreapplication.doimain.Person;
import com.pyco.coreapplication.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserDtoMapper {

    public UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    Person toPerson(UserDto userDto);

}
