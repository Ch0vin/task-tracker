package com.Tasks.Tasks.controller;

import com.Tasks.Tasks.domain.dto.TaskDto;
import com.Tasks.Tasks.domain.dto.TaskListDto;
import com.Tasks.Tasks.domain.entity.Task;
import com.Tasks.Tasks.mapper.TaskMapper;
import com.Tasks.Tasks.services.TaskServices;
import org.springframework.web.bind.annotation.*;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path="/task-lists/{task-list-id}/tasks")
public class TaskController {
    private final TaskServices taskServices;
    private final TaskMapper taskMapper;

    public TaskController(TaskServices taskServices, TaskMapper taskMapper) {
        this.taskServices = taskServices;
        this.taskMapper = taskMapper;
    }

    @GetMapping
    public List<TaskDto> listTasks(@PathVariable("task-list-id")UUID tasklistId){
        return taskServices.listTasks(tasklistId).stream()
                .map(taskMapper::toDTO).toList();
    }

    @PostMapping
    public TaskDto createTasks(@PathVariable("task-list-id")UUID tasklistId ,@RequestBody TaskDto taskDto){
      Task createdtask = taskServices.createTask(tasklistId,taskMapper.fromDto(taskDto));
      return taskMapper.toDTO(createdtask);
    }

    @GetMapping("/{task_id}")
    public Optional<TaskDto> getTask(
            @PathVariable("task_list_id") UUID taskListId, @PathVariable("task_id") UUID taskId) {
        return taskServices.getTask(taskListId, taskId).map(taskMapper::toDTO);
    }

    @PutMapping(path = "/{task_id}")
    public TaskDto updateTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID id,
            @RequestBody TaskDto taskDto) {
        Task updatedTask = taskServices.updateTask(
                taskListId, id, taskMapper.fromDto(taskDto));
        return taskMapper.toDTO(updatedTask);
    }
    @DeleteMapping(path = "/{task_id}")
    public void deleteTask(
            @PathVariable("task_list_id") UUID taskListId,
            @PathVariable("task_id") UUID taskId) {
        taskServices.deleteTask(taskListId, taskId);
    }

}
