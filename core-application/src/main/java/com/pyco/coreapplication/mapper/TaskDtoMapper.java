package com.pyco.coreapplication.mapper;

import com.pyco.coreapplication.doimain.Task;
import com.pyco.coreapplication.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskDtoMapper {

    public static TaskDtoMapper INSTANCE = Mappers.getMapper(TaskDtoMapper.class);

    Task toTask(TaskDto taskDto);

    void updateTask(TaskDto taskDto, @MappingTarget Task task);

}
