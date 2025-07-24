package com.Tasks.Tasks.services;

import com.Tasks.Tasks.domain.entity.TaskList;
import com.Tasks.Tasks.repositories.TaskListRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service
public interface TaskListService {
    List<TaskList> listTaskLists();

    TaskList createTaskList(TaskList taskList);
}
