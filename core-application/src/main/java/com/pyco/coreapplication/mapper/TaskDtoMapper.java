package com.pyco.coreapplication.mapper;

import com.pyco.coreapplication.domain.Task;
import com.pyco.coreapplication.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskDtoMapper {

    TaskDtoMapper INSTANCE = Mappers.getMapper(TaskDtoMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "done", ignore = true)
    Task toTask(TaskDto taskDto);

    void updateTask(TaskDto taskDto, @MappingTarget Task task);

}
