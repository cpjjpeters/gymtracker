package com.fitness.service;

import com.fitness.model.Exercise;
import com.fitness.model.Exercise.ExerciseType;
import com.fitness.model.Exercise.MuscleGroup;
import com.fitness.repository.ExerciseRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;

    public Exercise createExercise(Exercise exercise) {
        if (exerciseRepository.existsByName(exercise.getName())) {
            throw new IllegalArgumentException("Exercise with name '" + exercise.getName() + "' already exists");
        }
        return exerciseRepository.save(exercise);
    }

    @Transactional(readOnly = true)
    public Exercise getExerciseById(Long id) {
        return exerciseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public Exercise getExerciseByName(String name) {
        return exerciseRepository.findByName(name)
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found with name: " + name));
    }

    @Transactional(readOnly = true)
    public List<Exercise> getAllExercises() {
        return exerciseRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Exercise> getExercisesByMuscleGroup(MuscleGroup muscleGroup) {
        return exerciseRepository.findByMuscleGroup(muscleGroup);
    }

    @Transactional(readOnly = true)
    public List<Exercise> getExercisesByType(ExerciseType exerciseType) {
        return exerciseRepository.findByExerciseType(exerciseType);
    }

    @Transactional(readOnly = true)
    public List<Exercise> getExercisesByMuscleGroupAndType(MuscleGroup muscleGroup, ExerciseType exerciseType) {
        return exerciseRepository.findByMuscleGroupAndExerciseType(muscleGroup, exerciseType);
    }

    public Exercise updateExercise(Long id, Exercise exerciseDetails) {
        Exercise exercise = getExerciseById(id);
        
        // Check if name is being changed and if it already exists
        if (!exercise.getName().equals(exerciseDetails.getName()) && 
            exerciseRepository.existsByName(exerciseDetails.getName())) {
            throw new IllegalArgumentException("Exercise with name '" + exerciseDetails.getName() + "' already exists");
        }
        
        exercise.setName(exerciseDetails.getName());
        exercise.setDescription(exerciseDetails.getDescription());
        exercise.setMuscleGroup(exerciseDetails.getMuscleGroup());
        exercise.setExerciseType(exerciseDetails.getExerciseType());
        
        return exerciseRepository.save(exercise);
    }

    public void deleteExercise(Long id) {
        Exercise exercise = getExerciseById(id);
        exerciseRepository.delete(exercise);
    }

    @Transactional(readOnly = true)
    public boolean exerciseExists(String name) {
        return exerciseRepository.existsByName(name);
    }
}
