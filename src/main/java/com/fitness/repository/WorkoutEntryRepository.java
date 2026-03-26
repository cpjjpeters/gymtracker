package com.fitness.repository;

import com.fitness.model.WorkoutEntry;
import com.fitness.model.WorkoutEntry.GripType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutEntryRepository extends JpaRepository<WorkoutEntry, Long> {

    List<WorkoutEntry> findByWorkoutId(Long workoutId);

    List<WorkoutEntry> findByExerciseId(Long exerciseId);

    List<WorkoutEntry> findByGrip(GripType grip);

    @Query("SELECT we FROM WorkoutEntry we WHERE we.exercise.id = :exerciseId ORDER BY we.workout.date DESC, we.setNumber ASC")
    List<WorkoutEntry> findByExerciseIdOrderByDateDesc(Long exerciseId);

    @Query("SELECT we FROM WorkoutEntry we WHERE we.workout.id = :workoutId ORDER BY we.setNumber ASC")
    List<WorkoutEntry> findByWorkoutIdOrderBySetNumber(Long workoutId);

    @Query("SELECT DISTINCT we.grip FROM WorkoutEntry we WHERE we.exercise.id = :exerciseId")
    List<GripType> findDistinctGripsByExerciseId(Long exerciseId);
}
