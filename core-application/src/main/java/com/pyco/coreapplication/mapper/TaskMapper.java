package com.pyco.coreapplication.mapper;

import com.pyco.coreapplication.doimain.Task;
import com.pyco.coreapplication.dto.TaskDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TaskMapper {

    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    TaskDto toTaskDto(Task task);

}
