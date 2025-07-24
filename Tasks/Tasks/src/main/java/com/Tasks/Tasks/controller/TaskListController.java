package com.Tasks.Tasks.controller;

import com.Tasks.Tasks.domain.dto.TaskListDto;
import com.Tasks.Tasks.domain.entity.TaskList;
import com.Tasks.Tasks.mapper.TaskListMapper;
import com.Tasks.Tasks.services.TaskListService;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
@RestController
@RequestMapping(path = "/task-lists")
public class TaskListController {
    private final TaskListService taskListService;
    private final TaskListMapper taskListMapper;

    public TaskListController(TaskListService taskListService, TaskListMapper taskListMapper) {
        this.taskListService = taskListService;
        this.taskListMapper = taskListMapper;
    }
    @GetMapping
    public List<TaskListDto> listTaskLists() {
        return taskListService.listTaskLists()
                .stream()
                .map(taskListMapper::toDTO)
                .toList();
    }


    @PostMapping
    public TaskListDto createTaskList(@RequestBody TaskListDto taskListDto) {
        TaskList createdTaskList = taskListService.createTaskList(
                taskListMapper.fromDTO(taskListDto)
        );
        return taskListMapper.toDTO(createdTaskList);
    }
}