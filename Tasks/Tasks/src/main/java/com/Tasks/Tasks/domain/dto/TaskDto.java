package com.Tasks.Tasks.domain.dto;

import com.Tasks.Tasks.domain.entity.TaskPriority;
import com.Tasks.Tasks.domain.entity.TaskStatus;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,
        String title,
        String description,
        LocalDateTime dueDate,
        TaskPriority priority,
        TaskStatus status
) { }
