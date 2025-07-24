package com.Tasks.Tasks.services.impl;


import com.Tasks.Tasks.domain.entity.Task;
import com.Tasks.Tasks.domain.entity.TaskList;
import com.Tasks.Tasks.domain.entity.TaskPriority;
import com.Tasks.Tasks.domain.entity.TaskStatus;
import com.Tasks.Tasks.repositories.TaskListRepository;
import com.Tasks.Tasks.repositories.TaskRepository;
import com.Tasks.Tasks.services.TaskServices;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class TaskServiceImpl implements TaskServices {
    private final TaskRepository taskRepository;
    private final TaskListRepository taskListRepository;

    public TaskServiceImpl(TaskListRepository taskListRepository, TaskRepository taskRepository) {
        this.taskListRepository = taskListRepository;
        this.taskRepository = taskRepository;
    }



    @Override
    public List<Task> listTasks(UUID tasklistId) {
        return taskRepository.findByTaskListId(tasklistId);
    }

    @Override
    public Task createTask(UUID tasklistId, Task task) {
        if(null!=task.getId()){
            throw new IllegalArgumentException("Task already has an ID!");
        }
        if(null==task.getTitle()|| task.getTitle().isBlank()){
            throw new IllegalArgumentException("Task must have a title");
        }
        TaskPriority taskPriority = Optional.ofNullable(task.getPriority()).orElse(TaskPriority.MEDIUM);
        TaskStatus taskStatus= TaskStatus.OPEN;

       TaskList taskList= taskListRepository.findById(tasklistId).
               orElseThrow(()-> new IllegalArgumentException("Invalid Task List ID provided"));
        LocalDateTime now = LocalDateTime.now();
       Task tasktosave=new Task(
               null,
               task.getTitle(),
               task.getDescription(),
               task.getDueDate(),
               taskPriority,
               taskStatus,
               taskList,
               now,
               now

       );

       return taskRepository.save(tasktosave);
    }

    @Override
    public Optional<Task> getTask(UUID taskListId, UUID taskId) {
        return taskRepository.findByTaskListIdAndId(taskListId, taskId);
    }

    @Override
    public Task updateTask(UUID taskListId, UUID taskId, Task task) {
        if(null == task.getId()) {
            throw new IllegalArgumentException("Task must have ID!");
        }
        if(!Objects.equals(taskId, task.getId())) {
            throw new IllegalArgumentException("Task IDs do not match!");
        }
        if(null == task.getPriority()) {
            throw new IllegalArgumentException("Task must have a valid priority!");
        }
        if(null == task.getStatus()) {
            throw new IllegalArgumentException("Task must have a valid status!");
        }
        Task existingTask = taskRepository.findByTaskListIdAndId(
                taskListId, task.getId()
        ).orElseThrow(()-> new IllegalStateException("Task not found!"));
                existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setDueDate(task.getDueDate());
        existingTask.setPriority(task.getPriority());
        existingTask.setStatus(task.getStatus());
        existingTask.setUpdated(LocalDateTime.now());
        return taskRepository.save(existingTask);
    }


    @Transactional
    @Override
    public void deleteTask(UUID taskListId, UUID taskId) {
        taskRepository.deleteByTaskListIdAndId(taskListId, taskId);
    }
}
