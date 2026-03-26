package com.fitness.repository;

import com.fitness.model.Workout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findByDate(LocalDate date);

    List<Workout> findByDateBetween(LocalDate startDate, LocalDate endDate);

    @Query("SELECT w FROM Workout w ORDER BY w.date DESC")
    List<Workout> findAllOrderByDateDesc();

    @Query("SELECT w FROM Workout w WHERE w.date BETWEEN :startDate AND :endDate ORDER BY w.date DESC")
    List<Workout> findByDateBetweenOrderByDateDesc(LocalDate startDate, LocalDate endDate);
}
