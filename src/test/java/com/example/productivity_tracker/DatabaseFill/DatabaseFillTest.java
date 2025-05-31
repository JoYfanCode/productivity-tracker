package com.example.productivity_tracker.DatabaseFill;

import com.example.productivity_tracker.model.Activity;
import com.example.productivity_tracker.model.Category;
import com.example.productivity_tracker.model.User;
import com.example.productivity_tracker.service.ActivityService;
import com.example.productivity_tracker.service.CategoryService;
import com.example.productivity_tracker.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class DatabaseFillTest {
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ActivityService activityService;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testFillDatabase() {
        User user1 = new User("Иван", "ivan@example.com", "password123");
        User user2 = new User("Мария", "maria@example.com", "password123");
        User user3 = new User("Алексей", "alexey@example.com", "password123");
        
        user1 = userService.createUser(user1);
        user2 = userService.createUser(user2);
        user3 = userService.createUser(user3);

        Category work = new Category("Работа", "#FF0000");
        Category study = new Category("Учёба", "#00FF00");
        Category sport = new Category("Спорт", "#0000FF");
        
        work = categoryService.createCategory(work);
        study = categoryService.createCategory(study);
        sport = categoryService.createCategory(sport);

        Activity activity1 = new Activity(
            user1,
            work,
            LocalDateTime.now(),
            "Работа над новым функционалом"
        );

        Activity activity2 = new Activity(
            user2,
            study,
            LocalDateTime.now(),
            "Изучение документации и практика"
        );

        Activity activity3 = new Activity(
            user3,
            sport,
            LocalDateTime.now(),
            "Кардио и силовые упражнения"
        );

        activity1 = activityService.createActivity(activity1);
        activity2 = activityService.createActivity(activity2);
        activity3 = activityService.createActivity(activity3);

        assertNotNull(user1.getId());
        assertNotNull(user2.getId());
        assertNotNull(user3.getId());

        assertNotNull(work.getId());
        assertNotNull(study.getId());
        assertNotNull(sport.getId());

        assertNotNull(activity1.getId());
        assertNotNull(activity2.getId());
        assertNotNull(activity3.getId());
    }
} 