package com.Tasks.Tasks.mapper;

import com.Tasks.Tasks.domain.dto.TaskDto;
import com.Tasks.Tasks.domain.entity.Task;

public interface TaskMapper {

    Task fromDto(TaskDto taskDto);

    TaskDto toDTO(Task task);


}
