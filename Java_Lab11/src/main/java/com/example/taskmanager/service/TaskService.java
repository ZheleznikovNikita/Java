package com.example.taskmanager.service;

import com.example.taskmanager.model.Task;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TaskService {

    private final Map<Long, Task> tasks = new LinkedHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);
    private final ObjectMapper objectMapper;
    private final Path storagePath;

    public TaskService(
            ObjectMapper objectMapper,
            @Value("${taskmanager.storage-file:tasks.json}") String storageFile
    ) {
        this.objectMapper = objectMapper;
        this.storagePath = Path.of(storageFile);
    }

    @PostConstruct
    public synchronized void loadTasks() {
        try {
            if (Files.notExists(storagePath) || Files.size(storagePath) == 0) {
                persist();
                return;
            }

            List<Task> loadedTasks = objectMapper.readValue(
                    storagePath.toFile(),
                    new TypeReference<List<Task>>() {
                    }
            );

            tasks.clear();
            long maxId = 0;
            for (Task task : loadedTasks) {
                if (task.getId() == null) {
                    continue;
                }

                tasks.put(task.getId(), task);
                maxId = Math.max(maxId, task.getId());
            }
            nextId.set(maxId + 1);
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot load tasks from file", exception);
        }
    }

    public synchronized List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    public synchronized TaskPage findPage(int page, int size) {
        validatePageArguments(page, size);

        List<Task> allTasks = new ArrayList<>(tasks.values());
        int fromIndex = Math.min(page * size, allTasks.size());
        int toIndex = Math.min(fromIndex + size, allTasks.size());

        return new TaskPage(allTasks.subList(fromIndex, toIndex), allTasks.size());
    }

    public synchronized List<Task> searchByTitle(String title) {
        if (title == null || title.isBlank()) {
            return findAll();
        }

        String query = title.toLowerCase(Locale.ROOT);
        return tasks.values()
                .stream()
                .filter(task -> task.getTitle() != null)
                .filter(task -> task.getTitle().toLowerCase(Locale.ROOT).contains(query))
                .toList();
    }

    public synchronized Optional<Task> findById(Long id) {
        return Optional.ofNullable(tasks.get(id));
    }

    public synchronized Task save(Task task) {
        Task taskToSave = new Task(
                nextId.getAndIncrement(),
                task.getTitle(),
                task.getDescription(),
                task.isCompleted()
        );

        tasks.put(taskToSave.getId(), taskToSave);
        persist();
        return taskToSave;
    }

    public synchronized Optional<Task> update(Long id, Task updatedTask) {
        if (!tasks.containsKey(id)) {
            return Optional.empty();
        }

        Task taskToSave = new Task(
                id,
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.isCompleted()
        );

        tasks.put(id, taskToSave);
        persist();
        return Optional.of(taskToSave);
    }

    public synchronized boolean delete(Long id) {
        if (tasks.remove(id) == null) {
            return false;
        }

        persist();
        return true;
    }

    private void validatePageArguments(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("Page must be zero or positive");
        }
        if (size <= 0) {
            throw new IllegalArgumentException("Size must be positive");
        }
    }

    private void persist() {
        // Дополнительное задание: синхронное сохранение актуального списка задач в JSON-файл.
        try {
            Path parent = storagePath.toAbsolutePath().getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }

            objectMapper.writerWithDefaultPrettyPrinter()
                    .writeValue(storagePath.toFile(), tasks.values());
        } catch (IOException exception) {
            throw new IllegalStateException("Cannot save tasks to file", exception);
        }
    }
}
