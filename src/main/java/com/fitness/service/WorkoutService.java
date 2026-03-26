package com.fitness.service;

import com.fitness.model.Workout;
import com.fitness.model.WorkoutEntry;
import com.fitness.repository.WorkoutRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkoutService {

    private final WorkoutRepository workoutRepository;

    public Workout createWorkout(Workout workout) {
        return workoutRepository.save(workout);
    }

    @Transactional(readOnly = true)
    public Workout getWorkoutById(Long id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workout not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAllOrderByDateDesc();
    }

    @Transactional(readOnly = true)
    public List<Workout> getWorkoutsByDate(LocalDate date) {
        return workoutRepository.findByDate(date);
    }

    @Transactional(readOnly = true)
    public List<Workout> getWorkoutsByDateRange(LocalDate startDate, LocalDate endDate) {
        return workoutRepository.findByDateBetweenOrderByDateDesc(startDate, endDate);
    }

    public Workout updateWorkout(Long id, Workout workoutDetails) {
        Workout workout = getWorkoutById(id);
        
        workout.setDate(workoutDetails.getDate());
        workout.setNotes(workoutDetails.getNotes());
        workout.setDurationMinutes(workoutDetails.getDurationMinutes());
        
        return workoutRepository.save(workout);
    }

    public void deleteWorkout(Long id) {
        Workout workout = getWorkoutById(id);
        workoutRepository.delete(workout);
    }

    public Workout addEntryToWorkout(Long workoutId, WorkoutEntry entry) {
        Workout workout = getWorkoutById(workoutId);
        workout.addEntry(entry);
        return workoutRepository.save(workout);
    }

    public Workout removeEntryFromWorkout(Long workoutId, Long entryId) {
        Workout workout = getWorkoutById(workoutId);
        WorkoutEntry entry = workout.getEntries().stream()
                .filter(e -> e.getId().equals(entryId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Entry not found with id: " + entryId + " in workout: " + workoutId));
        
        workout.removeEntry(entry);
        return workoutRepository.save(workout);
    }

    @Transactional(readOnly = true)
    public long getWorkoutCount() {
        return workoutRepository.count();
    }
}
