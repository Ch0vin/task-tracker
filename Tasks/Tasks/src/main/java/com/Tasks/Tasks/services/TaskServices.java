package com.Tasks.Tasks.services;


import com.Tasks.Tasks.domain.entity.Task;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskServices {
    List<Task> listTasks(UUID tasklistId);
    Task createTask(UUID tasklistId,Task task);
    Optional<Task> getTask(UUID taskListId, UUID taskId);
    Task updateTask(UUID taskListId, UUID taskId, Task task);
    void deleteTask(UUID taskListId, UUID taskId);
}
