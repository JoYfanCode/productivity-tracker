package com.example.productivity_tracker.controller;

import com.example.productivity_tracker.model.Activity;
import com.example.productivity_tracker.model.Category;
import com.example.productivity_tracker.model.User;
import com.example.productivity_tracker.service.ActivityService;
import com.example.productivity_tracker.service.CategoryService;
import com.example.productivity_tracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/activities")
@Tag(name = "Активности", description = "Методы для работы с активностями")
public class ActivityController {
    private final ActivityService activityService;
    private final UserService userService;
    private final CategoryService categoryService;

    public ActivityController(ActivityService activityService, UserService userService, CategoryService categoryService) {
        this.activityService = activityService;
        this.userService = userService;
        this.categoryService = categoryService;
    }

    @PostMapping
    @Operation(summary = "Создать новую активность", description = "Создает новую активность и возвращает информацию о ней.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Активность успешно создана"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<Activity> createActivity(@RequestBody Activity activity) {
        return ResponseEntity.ok(activityService.createActivity(activity));
    }

    @GetMapping
    @Operation(summary = "Вывести все активности", description = "Возвращает список всех активностей.")
    @ApiResponse(responseCode = "200", description = "Список активностей успешно получен")
    public ResponseEntity<List<Activity>> getAllActivities() {
        return ResponseEntity.ok(activityService.findAllActivities());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить активность по ID", description = "Возвращает информацию об активности.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Активность найдена"),
            @ApiResponse(responseCode = "404", description = "Активность не найдена")
    })
    public ResponseEntity<Activity> getActivityById(
            @Parameter(description = "ID активности", required = true) @PathVariable Long id
    ) {
        Optional<Activity> activityOpt = activityService.findActivityById(id);
        return activityOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Получить активности пользователя", description = "Возвращает список активностей конкретного пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Активности успешно получены"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<List<Activity>> getActivitiesByUser(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long userId
    ) {
        Optional<User> userOpt = userService.findUserById(userId);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(activityService.findActivitiesByUser(userOpt.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/category/{categoryId}")
    @Operation(summary = "Получить активности по категории", description = "Возвращает список активностей определенной категории.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Активности успешно получены"),
            @ApiResponse(responseCode = "404", description = "Категория не найдена")
    })
    public ResponseEntity<List<Activity>> getActivitiesByCategory(
            @Parameter(description = "ID категории", required = true) @PathVariable Long categoryId
    ) {
        Optional<Category> categoryOpt = categoryService.findCategoryById(categoryId);
        if (categoryOpt.isPresent()) {
            return ResponseEntity.ok(activityService.findActivitiesByCategory(categoryOpt.get()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/period")
    @Operation(summary = "Получить активности за период", description = "Возвращает список активностей за указанный период времени.")
    @ApiResponse(responseCode = "200", description = "Активности успешно получены")
    public ResponseEntity<List<Activity>> getActivitiesByPeriod(
            @Parameter(description = "Начало периода", required = true) @RequestParam LocalDateTime start,
            @Parameter(description = "Конец периода", required = true) @RequestParam LocalDateTime end
    ) {
        return ResponseEntity.ok(activityService.findActivitiesByTimePeriod(start, end));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить активность", description = "Обновляет информацию об активности.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Активность успешно обновлена"),
            @ApiResponse(responseCode = "404", description = "Активность не найдена")
    })
    public ResponseEntity<Activity> updateActivity(
            @Parameter(description = "ID активности", required = true) @PathVariable Long id,
            @RequestBody Activity activity
    ) {
        Optional<Activity> existingActivityOpt = activityService.findActivityById(id);
        if (existingActivityOpt.isPresent()) {
            activity.setId(id);
            return ResponseEntity.ok(activityService.updateActivity(activity));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить активность", description = "Удаляет активность по её ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Активность успешно удалена"),
            @ApiResponse(responseCode = "404", description = "Активность не найдена")
    })
    public ResponseEntity<Void> deleteActivity(
            @Parameter(description = "ID активности", required = true) @PathVariable Long id
    ) {
        Optional<Activity> activityOpt = activityService.findActivityById(id);
        if (activityOpt.isPresent()) {
            activityService.deleteActivity(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 