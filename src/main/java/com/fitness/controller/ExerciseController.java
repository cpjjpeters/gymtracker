package com.fitness.controller;

import com.fitness.model.Exercise;
import com.fitness.model.Exercise.ExerciseType;
import com.fitness.model.Exercise.MuscleGroup;
import com.fitness.service.ExerciseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity<Exercise> createExercise(@Valid @RequestBody Exercise exercise) {
        Exercise createdExercise = exerciseService.createExercise(exercise);
        return new ResponseEntity<>(createdExercise, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Exercise> getExerciseById(@PathVariable Long id) {
        Exercise exercise = exerciseService.getExerciseById(id);
        return ResponseEntity.ok(exercise);
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Exercise> getExerciseByName(@PathVariable String name) {
        Exercise exercise = exerciseService.getExerciseByName(name);
        return ResponseEntity.ok(exercise);
    }

    @GetMapping
    public ResponseEntity<List<Exercise>> getAllExercises() {
        List<Exercise> exercises = exerciseService.getAllExercises();
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/muscle-group/{muscleGroup}")
    public ResponseEntity<List<Exercise>> getExercisesByMuscleGroup(@PathVariable MuscleGroup muscleGroup) {
        List<Exercise> exercises = exerciseService.getExercisesByMuscleGroup(muscleGroup);
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/type/{exerciseType}")
    public ResponseEntity<List<Exercise>> getExercisesByType(@PathVariable ExerciseType exerciseType) {
        List<Exercise> exercises = exerciseService.getExercisesByType(exerciseType);
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Exercise>> getExercisesByMuscleGroupAndType(
            @RequestParam MuscleGroup muscleGroup,
            @RequestParam ExerciseType exerciseType) {
        List<Exercise> exercises = exerciseService.getExercisesByMuscleGroupAndType(muscleGroup, exerciseType);
        return ResponseEntity.ok(exercises);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable Long id, @Valid @RequestBody Exercise exercise) {
        Exercise updatedExercise = exerciseService.updateExercise(id, exercise);
        return ResponseEntity.ok(updatedExercise);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/exists/{name}")
    public ResponseEntity<Boolean> exerciseExists(@PathVariable String name) {
        boolean exists = exerciseService.exerciseExists(name);
        return ResponseEntity.ok(exists);
    }
}
