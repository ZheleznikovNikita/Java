package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;

import java.util.List;

public record TaskPage(List<Task> tasks, long total) {
}
