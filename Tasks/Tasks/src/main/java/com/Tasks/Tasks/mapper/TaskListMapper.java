package com.Tasks.Tasks.mapper;

import com.Tasks.Tasks.domain.dto.TaskListDto;
import com.Tasks.Tasks.domain.entity.TaskList;

public interface TaskListMapper {

 TaskList fromDTO(TaskListDto taskListDto);

 TaskListDto toDTO(TaskList taskList);
}
