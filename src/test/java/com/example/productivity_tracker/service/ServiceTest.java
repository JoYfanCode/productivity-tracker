package com.example.productivity_tracker.service;

import com.example.productivity_tracker.model.Activity;
import com.example.productivity_tracker.model.Category;
import com.example.productivity_tracker.model.User;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class ServiceTest {
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ActivityService activityService;

    private User testUser;
    private Category testCategory;
    private Activity testActivity;

    @BeforeEach
    public void setUp() {
        testUser = new User("Тестовый пользователь", "test@example.com", "password123");
        testUser = userService.createUser(testUser);

        testCategory = new Category("Тестовая категория", "#FF0000");
        testCategory = categoryService.createCategory(testCategory);

        testActivity = new Activity(
            testUser,
            testCategory,
            LocalDateTime.now(),
            "Описание тестовой активности"
        );
        testActivity = activityService.createActivity(testActivity);
    }

    @Test
    public void testFindAllUsers() {
        List<User> users = userService.findAllUsers();
        assertNotNull(users);
        assertFalse(users.isEmpty());
    }

    @Test
    public void testFindUserById() {
        Optional<User> userOpt = userService.findUserById(testUser.getId());
        assertTrue(userOpt.isPresent());
        assertEquals(testUser.getId(), userOpt.get().getId());
    }

    @Test
    public void testFindUserByUsername() {
        User user = userService.findUserByUsername("Тестовый пользователь");
        assertNotNull(user);
        assertEquals("Тестовый пользователь", user.getUsername());
    }

    @Test
    public void testFindAllCategories() {
        List<Category> categories = categoryService.findAllCategories();
        assertNotNull(categories);
        assertFalse(categories.isEmpty());
    }

    @Test
    public void testFindCategoryById() {
        Optional<Category> categoryOpt = categoryService.findCategoryById(testCategory.getId());
        assertTrue(categoryOpt.isPresent());
        assertEquals(testCategory.getId(), categoryOpt.get().getId());
    }

    @Test
    public void testFindCategoryByName() {
        Category category = categoryService.findCategoryByName("Тестовая категория");
        assertNotNull(category);
        assertEquals("Тестовая категория", category.getName());
    }

    @Test
    public void testFindAllActivities() {
        List<Activity> activities = activityService.findAllActivities();
        assertNotNull(activities);
        assertFalse(activities.isEmpty());
    }

    @Test
    public void testFindActivityById() {
        Optional<Activity> activityOpt = activityService.findActivityById(testActivity.getId());
        assertTrue(activityOpt.isPresent());
        assertEquals(testActivity.getId(), activityOpt.get().getId());
    }

    @Test
    public void testFindActivitiesByUser() {
        List<Activity> activities = activityService.findActivitiesByUser(testUser);
        assertNotNull(activities);
        assertFalse(activities.isEmpty());
        assertTrue(activities.stream().allMatch(a -> a.getUser().getId().equals(testUser.getId())));
    }

    @Test
    public void testFindActivitiesByCategory() {
        List<Activity> activities = activityService.findActivitiesByCategory(testCategory);
        assertNotNull(activities);
        assertFalse(activities.isEmpty());
        assertTrue(activities.stream().allMatch(a -> a.getCategory().getId().equals(testCategory.getId())));
    }

    @Test
    @Transactional
    public void testUpdateActivity() {
        String newDescription = "Обновленное описание активности";
        testActivity.setDescription(newDescription);
        Activity updatedActivity = activityService.updateActivity(testActivity);
        
        assertNotNull(updatedActivity);
        assertEquals(newDescription, updatedActivity.getDescription());
    }

    @Test
    @Transactional
    public void testDeleteActivity() {
        activityService.deleteActivity(testActivity.getId());
        Optional<Activity> deletedActivity = activityService.findActivityById(testActivity.getId());
        assertTrue(deletedActivity.isEmpty());
    }
} 