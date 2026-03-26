package com.fitness.repository;

import com.fitness.model.Exercise;
import com.fitness.model.Exercise.MuscleGroup;
import com.fitness.model.Exercise.ExerciseType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    Optional<Exercise> findByName(String name);

    List<Exercise> findByMuscleGroup(MuscleGroup muscleGroup);

    List<Exercise> findByExerciseType(ExerciseType exerciseType);

    List<Exercise> findByMuscleGroupAndExerciseType(MuscleGroup muscleGroup, ExerciseType exerciseType);

    boolean existsByName(String name);
}
