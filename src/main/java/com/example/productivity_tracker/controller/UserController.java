package com.example.productivity_tracker.controller;

import com.example.productivity_tracker.model.Activity;
import com.example.productivity_tracker.model.User;
import com.example.productivity_tracker.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Пользователи", description = "Методы для работы с пользователями")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    @Operation(summary = "Создать нового пользователя", description = "Создает нового пользователя и возвращает его данные.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации данных"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера")
    })
    public ResponseEntity<User> createUser(@RequestBody User user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @GetMapping
    @Operation(summary = "Вывести всех пользователей", description = "Возвращает список всех пользователей.")
    @ApiResponse(responseCode = "200", description = "Список пользователей успешно получен")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.findAllUsers());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Получить пользователя по ID", description = "Возвращает информацию о пользователе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<User> getUserById(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long id
    ) {
        Optional<User> userOpt = userService.findUserById(id);
        return userOpt.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    @Operation(summary = "Найти пользователя по имени пользователя", description = "Ищет пользователей по имени пользователя, возвращает все подходящие варианты.")
    @ApiResponse(responseCode = "200", description = "Результаты поиска успешно получены")
    public ResponseEntity<User> findUserByUsername(
            @Parameter(description = "Имя пользователя", required = true) @RequestParam String username
    ) {
        User user = userService.findUserByUsername(username);
        return user != null ? ResponseEntity.ok(user) : ResponseEntity.notFound().build();
    }

    @GetMapping("/{id}/activities")
    @Operation(summary = "Получить все активности пользователя по его ID", description = "Возвращает список активностей пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Активности успешно получены"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<List<Activity>> getUserActivities(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long id
    ) {
        Optional<User> userOpt = userService.findUserById(id);
        if (userOpt.isPresent()) {
            return ResponseEntity.ok(userOpt.get().getActivities());
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    @Operation(summary = "Обновить данные пользователя", description = "Обновляет информацию о пользователе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Данные пользователя успешно обновлены"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<User> updateUser(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long id,
            @RequestBody User user
    ) {
        Optional<User> existingUserOpt = userService.findUserById(id);
        if (existingUserOpt.isPresent()) {
            user.setId(id);
            return ResponseEntity.ok(userService.updateUser(user));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по его ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID пользователя", required = true) @PathVariable Long id
    ) {
        Optional<User> userOpt = userService.findUserById(id);
        if (userOpt.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
} 