package com.example.productivity_tracker.repository;

import com.example.productivity_tracker.model.Activity;
import com.example.productivity_tracker.model.User;
import com.example.productivity_tracker.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    List<Activity> findByUser(User user);
    List<Activity> findByCategory(Category category);
    List<Activity> findByUserAndCategory(User user, Category category);
    List<Activity> findByTimestampBetween(LocalDateTime start, LocalDateTime end);
    List<Activity> findByUserAndTimestampBetween(User user, LocalDateTime start, LocalDateTime end);
} 