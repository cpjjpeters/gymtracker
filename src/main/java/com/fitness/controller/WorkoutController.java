package com.fitness.controller;

import com.fitness.model.Workout;
import com.fitness.model.WorkoutEntry;
import com.fitness.service.WorkoutService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/workouts")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WorkoutController {

    private final WorkoutService workoutService;

    @PostMapping
    public ResponseEntity<Workout> createWorkout(@Valid @RequestBody Workout workout) {
        Workout createdWorkout = workoutService.createWorkout(workout);
        return new ResponseEntity<>(createdWorkout, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Workout> getWorkoutById(@PathVariable Long id) {
        Workout workout = workoutService.getWorkoutById(id);
        return ResponseEntity.ok(workout);
    }

    @GetMapping
    public ResponseEntity<List<Workout>> getAllWorkouts() {
        List<Workout> workouts = workoutService.getAllWorkouts();
        return ResponseEntity.ok(workouts);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Workout>> getWorkoutsByDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Workout> workouts = workoutService.getWorkoutsByDate(date);
        return ResponseEntity.ok(workouts);
    }

    @GetMapping("/date-range")
    public ResponseEntity<List<Workout>> getWorkoutsByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Workout> workouts = workoutService.getWorkoutsByDateRange(startDate, endDate);
        return ResponseEntity.ok(workouts);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Workout> updateWorkout(@PathVariable Long id, @Valid @RequestBody Workout workout) {
        Workout updatedWorkout = workoutService.updateWorkout(id, workout);
        return ResponseEntity.ok(updatedWorkout);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkout(@PathVariable Long id) {
        workoutService.deleteWorkout(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{workoutId}/entries")
    public ResponseEntity<Workout> addEntryToWorkout(
            @PathVariable Long workoutId,
            @Valid @RequestBody WorkoutEntry entry) {
        Workout workout = workoutService.addEntryToWorkout(workoutId, entry);
        return ResponseEntity.ok(workout);
    }

    @DeleteMapping("/{workoutId}/entries/{entryId}")
    public ResponseEntity<Workout> removeEntryFromWorkout(
            @PathVariable Long workoutId,
            @PathVariable Long entryId) {
        Workout workout = workoutService.removeEntryFromWorkout(workoutId, entryId);
        return ResponseEntity.ok(workout);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getWorkoutCount() {
        long count = workoutService.getWorkoutCount();
        return ResponseEntity.ok(count);
    }
}
