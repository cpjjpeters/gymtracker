package com.fitness.controller;

import com.fitness.model.WorkoutEntry;
import com.fitness.model.WorkoutEntry.GripType;
import com.fitness.service.WorkoutEntryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/entries")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WorkoutEntryController {

    private final WorkoutEntryService workoutEntryService;

    @PostMapping
    public ResponseEntity<WorkoutEntry> createEntry(@Valid @RequestBody WorkoutEntry entry) {
        WorkoutEntry createdEntry = workoutEntryService.createEntry(entry);
        return new ResponseEntity<>(createdEntry, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutEntry> getEntryById(@PathVariable Long id) {
        WorkoutEntry entry = workoutEntryService.getEntryById(id);
        return ResponseEntity.ok(entry);
    }

    @GetMapping("/workout/{workoutId}")
    public ResponseEntity<List<WorkoutEntry>> getEntriesByWorkoutId(@PathVariable Long workoutId) {
        List<WorkoutEntry> entries = workoutEntryService.getEntriesByWorkoutId(workoutId);
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/exercise/{exerciseId}")
    public ResponseEntity<List<WorkoutEntry>> getEntriesByExerciseId(@PathVariable Long exerciseId) {
        List<WorkoutEntry> entries = workoutEntryService.getEntriesByExerciseId(exerciseId);
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/grip/{grip}")
    public ResponseEntity<List<WorkoutEntry>> getEntriesByGrip(@PathVariable GripType grip) {
        List<WorkoutEntry> entries = workoutEntryService.getEntriesByGrip(grip);
        return ResponseEntity.ok(entries);
    }

    @GetMapping("/exercise/{exerciseId}/grips")
    public ResponseEntity<List<GripType>> getDistinctGripsByExerciseId(@PathVariable Long exerciseId) {
        List<GripType> grips = workoutEntryService.getDistinctGripsByExerciseId(exerciseId);
        return ResponseEntity.ok(grips);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutEntry> updateEntry(@PathVariable Long id, @Valid @RequestBody WorkoutEntry entry) {
        WorkoutEntry updatedEntry = workoutEntryService.updateEntry(id, entry);
        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable Long id) {
        workoutEntryService.deleteEntry(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/count")
    public ResponseEntity<Long> getEntryCount() {
        long count = workoutEntryService.getEntryCount();
        return ResponseEntity.ok(count);
    }

    @GetMapping("/exercise/{exerciseId}/count")
    public ResponseEntity<Long> getEntryCountByExerciseId(@PathVariable Long exerciseId) {
        long count = workoutEntryService.getEntryCountByExerciseId(exerciseId);
        return ResponseEntity.ok(count);
    }
}
