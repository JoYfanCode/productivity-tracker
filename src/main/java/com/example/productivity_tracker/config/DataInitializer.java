package com.example.productivity_tracker.config;

import com.example.productivity_tracker.model.Activity;
import com.example.productivity_tracker.model.Category;
import com.example.productivity_tracker.model.User;
import com.example.productivity_tracker.repository.ActivityRepository;
import com.example.productivity_tracker.repository.CategoryRepository;
import com.example.productivity_tracker.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            UserRepository userRepository,
            CategoryRepository categoryRepository,
            ActivityRepository activityRepository,
            BCryptPasswordEncoder passwordEncoder) {
        
        return args -> {
            if (userRepository.count() == 0) {
                System.out.println("Инициализация тестовых данных...");
                
                // Создаем пользователей
                User user1 = new User("user1", "user1@example.com", passwordEncoder.encode("password"));
                User user2 = new User("user2", "user2@example.com", passwordEncoder.encode("password"));
                
                userRepository.saveAll(Arrays.asList(user1, user2));
                System.out.println("Создано пользователей: " + userRepository.count());
                
                // Создаем категории
                Category productive = new Category("Продуктивное", "зеленый");
                Category neutral = new Category("Нейтральное", "серый");
                Category entertainment = new Category("Развлекательное", "красный");
                
                categoryRepository.saveAll(Arrays.asList(productive, neutral, entertainment));
                System.out.println("Создано категорий: " + categoryRepository.count());
                
                // Создаем активности
                LocalDateTime now = LocalDateTime.now();
                
                Activity activity1 = new Activity(user1, productive, now.minusDays(1), "Работа над проектом");
                Activity activity2 = new Activity(user1, neutral, now.minusHours(5), "Обед");
                Activity activity3 = new Activity(user1, entertainment, now.minusHours(2), "Просмотр фильма");
                
                Activity activity4 = new Activity(user2, productive, now.minusDays(2), "Учеба");
                Activity activity5 = new Activity(user2, neutral, now.minusHours(8), "Совещание");
                Activity activity6 = new Activity(user2, entertainment, now.minusHours(4), "Игры");
                
                activityRepository.saveAll(Arrays.asList(
                    activity1, activity2, activity3, activity4, activity5, activity6
                ));
                System.out.println("Создано активностей: " + activityRepository.count());
                
                System.out.println("Инициализация данных завершена!");
            } else {
                System.out.println("Пропуск инициализации данных: в базе уже есть " + userRepository.count() + " пользователей.");
            }
        };
    }
} 