package com.Tasks.Tasks.mapper.impl;

import com.Tasks.Tasks.domain.dto.TaskListDto;
import com.Tasks.Tasks.domain.entity.Task;
import com.Tasks.Tasks.domain.entity.TaskList;
import com.Tasks.Tasks.domain.entity.TaskStatus;
import com.Tasks.Tasks.mapper.TaskListMapper;
import com.Tasks.Tasks.mapper.TaskMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class TaskListMapperImpl implements TaskListMapper {
    private final TaskMapper taskMapper;

    public TaskListMapperImpl(TaskMapper taskMapper) {
        this.taskMapper = taskMapper;
    }

    @Override
    public TaskList fromDTO(TaskListDto taskListDto) {
        return new TaskList(
                taskListDto.id(),
                taskListDto.title(),
                taskListDto.description(),
                Optional.ofNullable(taskListDto.tasks())
                        .map(tasks-> tasks.stream()
                                .map(taskMapper::fromDto)
                                .toList())
                        .orElse(null),
                null,
                null
        );
    }

    @Override
    public TaskListDto toDTO(TaskList taskList) {
        return new TaskListDto(
                taskList.getId(),
                taskList.getTitle(),
                taskList.getDescription(),
                Optional.ofNullable(taskList.getTasks())
                        .map(List::size)
                        .orElse(0),
                CalculateTaskListProgress(taskList.getTasks()),
                Optional.ofNullable(taskList.getTasks())
                        .map(tasks ->
                                tasks.stream().map(taskMapper::toDTO).toList())
                        .orElse(null)
        );
    }

    private Double CalculateTaskListProgress(List<Task> tasks){
        if (null==tasks){
            return null;
        }

       long closedTaskList= tasks.stream().filter(
               task -> TaskStatus.CLOSED==task.getStatus()
       ).count();

        return (double)closedTaskList / tasks.size();
    }
}
