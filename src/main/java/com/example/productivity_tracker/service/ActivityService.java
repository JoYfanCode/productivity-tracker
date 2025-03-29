package com.example.productivity_tracker.service;

import com.example.productivity_tracker.model.Activity;
import com.example.productivity_tracker.model.Category;
import com.example.productivity_tracker.model.User;
import com.example.productivity_tracker.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    private final ActivityRepository activityRepository;

    @Autowired
    public ActivityService(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    public List<Activity> findAllActivities() {
        return activityRepository.findAll();
    }

    public Optional<Activity> findActivityById(Long id) {
        return activityRepository.findById(id);
    }

    public List<Activity> findActivitiesByUser(User user) {
        return activityRepository.findByUser(user);
    }

    public List<Activity> findActivitiesByCategory(Category category) {
        return activityRepository.findByCategory(category);
    }

    public List<Activity> findActivitiesByUserAndCategory(User user, Category category) {
        return activityRepository.findByUserAndCategory(user, category);
    }

    public List<Activity> findActivitiesByTimePeriod(LocalDateTime start, LocalDateTime end) {
        return activityRepository.findByTimestampBetween(start, end);
    }

    public List<Activity> findActivitiesByUserAndTimePeriod(User user, LocalDateTime start, LocalDateTime end) {
        return activityRepository.findByUserAndTimestampBetween(user, start, end);
    }

    public Activity createActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public Activity updateActivity(Activity activity) {
        return activityRepository.save(activity);
    }

    public void deleteActivity(Long id) {
        activityRepository.deleteById(id);
    }
} 