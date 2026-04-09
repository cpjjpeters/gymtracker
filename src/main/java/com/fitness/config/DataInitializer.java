package com.fitness.config;

import com.fitness.model.Exercise;
import com.fitness.model.Exercise.ExerciseType;
import com.fitness.model.Exercise.MuscleGroup;
import com.fitness.model.Workout;
import com.fitness.model.WorkoutEntry;
import com.fitness.model.WorkoutEntry.GripType;
import com.fitness.repository.ExerciseRepository;
import com.fitness.repository.WorkoutEntryRepository;
import com.fitness.repository.WorkoutRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutRepository workoutRepository;
    private final WorkoutEntryRepository workoutEntryRepository;

    @Override
    public void run(String... args) {
        if (exerciseRepository.count() == 0) {
            log.info("Initializing basic exercise dataset...");
            initializeExercises();
            log.info("Basic exercise dataset initialized successfully!");
        } else {
            log.info("Exercise dataset already exists, skipping initialization.");
        }

        if (workoutRepository.count() == 0) {
            log.info("Initializing sample workout and workout entry...");
            initializeWorkouts();
            log.info("Sample workout and workout entry initialized successfully!");
        } else {
            log.info("Workouts already exist, skipping initialization.");
        }
    }

    private void initializeWorkouts() {
        Optional<Exercise> benchPress = exerciseRepository.findByName("Bench Press");

        if (benchPress.isPresent()) {
            Workout workout = new Workout();
            workout.setDate(LocalDate.now());
            workout.setNotes("First sample workout");
            workout.setDurationMinutes(60);
            Workout savedWorkout = workoutRepository.save(workout);

            WorkoutEntry entry = new WorkoutEntry();
            entry.setWorkout(savedWorkout);
            entry.setExercise(benchPress.get());
            entry.setSetNumber(1);
            entry.setRepetitions(10);
            entry.setWeight(new BigDecimal("60.00"));
            entry.setGrip(GripType.OVERHAND);
            entry.setNotes("First sample entry");
            workoutEntryRepository.save(entry);
        } else {
            log.warn("Exercise 'Bench Press' not found, skipping workout initialization.");
        }
    }

    private void initializeExercises() {
        List<Exercise> exercises = Arrays.asList(
            // Chest exercises
            createExercise("Bench Press", "Flat barbell bench press", MuscleGroup.CHEST, ExerciseType.COMPOUND),
            createExercise("Incline Bench Press", "Incline barbell bench press", MuscleGroup.CHEST, ExerciseType.COMPOUND),
            createExercise("Decline Bench Press", "Decline barbell bench press", MuscleGroup.CHEST, ExerciseType.COMPOUND),
            createExercise("Dumbbell Press", "Flat dumbbell bench press", MuscleGroup.CHEST, ExerciseType.COMPOUND),
            createExercise("Incline Dumbbell Press", "Incline dumbbell bench press", MuscleGroup.CHEST, ExerciseType.COMPOUND),
            createExercise("Cable Flyes", "Cable crossover flyes", MuscleGroup.CHEST, ExerciseType.ISOLATION),
            createExercise("Dumbbell Flyes", "Flat dumbbell flyes", MuscleGroup.CHEST, ExerciseType.ISOLATION),
            createExercise("Push-ups", "Standard push-ups", MuscleGroup.CHEST, ExerciseType.BODYWEIGHT),

            // Back exercises
            createExercise("Deadlift", "Conventional deadlift", MuscleGroup.BACK, ExerciseType.COMPOUND),
            createExercise("Barbell Row", "Bent-over barbell row", MuscleGroup.BACK, ExerciseType.COMPOUND),
            createExercise("Dumbbell Row", "Single-arm dumbbell row", MuscleGroup.BACK, ExerciseType.COMPOUND),
            createExercise("Lat Pulldown", "Wide-grip lat pulldown", MuscleGroup.BACK, ExerciseType.COMPOUND),
            createExercise("Pull-ups", "Standard pull-ups", MuscleGroup.BACK, ExerciseType.BODYWEIGHT),
            createExercise("Seated Cable Row", "Seated cable row", MuscleGroup.BACK, ExerciseType.COMPOUND),
            createExercise("Face Pulls", "Cable face pulls", MuscleGroup.BACK, ExerciseType.ISOLATION),

            // Shoulder exercises
            createExercise("Overhead Press", "Barbell overhead press", MuscleGroup.SHOULDERS, ExerciseType.COMPOUND),
            createExercise("Dumbbell Shoulder Press", "Seated dumbbell shoulder press", MuscleGroup.SHOULDERS, ExerciseType.COMPOUND),
            createExercise("Lateral Raises", "Dumbbell lateral raises", MuscleGroup.SHOULDERS, ExerciseType.ISOLATION),
            createExercise("Front Raises", "Dumbbell front raises", MuscleGroup.SHOULDERS, ExerciseType.ISOLATION),
            createExercise("Rear Delt Flyes", "Bent-over rear delt flyes", MuscleGroup.SHOULDERS, ExerciseType.ISOLATION),
            createExercise("Arnold Press", "Arnold dumbbell press", MuscleGroup.SHOULDERS, ExerciseType.COMPOUND),

            // Biceps exercises
            createExercise("Barbell Curl", "Standing barbell curl", MuscleGroup.BICEPS, ExerciseType.ISOLATION),
            createExercise("Dumbbell Curl", "Standing dumbbell curl", MuscleGroup.BICEPS, ExerciseType.ISOLATION),
            createExercise("Hammer Curl", "Dumbbell hammer curl", MuscleGroup.BICEPS, ExerciseType.ISOLATION),
            createExercise("Preacher Curl", "Barbell preacher curl", MuscleGroup.BICEPS, ExerciseType.ISOLATION),
            createExercise("Cable Curl", "Cable bicep curl", MuscleGroup.BICEPS, ExerciseType.ISOLATION),
            createExercise("Concentration Curl", "Seated concentration curl", MuscleGroup.BICEPS, ExerciseType.ISOLATION),

            // Triceps exercises
            createExercise("Close-Grip Bench Press", "Close-grip barbell bench press", MuscleGroup.TRICEPS, ExerciseType.COMPOUND),
            createExercise("Tricep Pushdown", "Cable tricep pushdown", MuscleGroup.TRICEPS, ExerciseType.ISOLATION),
            createExercise("Overhead Tricep Extension", "Dumbbell overhead tricep extension", MuscleGroup.TRICEPS, ExerciseType.ISOLATION),
            createExercise("Skull Crushers", "Barbell skull crushers", MuscleGroup.TRICEPS, ExerciseType.ISOLATION),
            createExercise("Dips", "Parallel bar dips", MuscleGroup.TRICEPS, ExerciseType.BODYWEIGHT),
            createExercise("Tricep Kickback", "Dumbbell tricep kickback", MuscleGroup.TRICEPS, ExerciseType.ISOLATION),

            // Legs exercises
            createExercise("Squat", "Barbell back squat", MuscleGroup.LEGS, ExerciseType.COMPOUND),
            createExercise("Front Squat", "Barbell front squat", MuscleGroup.LEGS, ExerciseType.COMPOUND),
            createExercise("Leg Press", "Machine leg press", MuscleGroup.LEGS, ExerciseType.COMPOUND),
            createExercise("Romanian Deadlift", "Barbell Romanian deadlift", MuscleGroup.LEGS, ExerciseType.COMPOUND),
            createExercise("Leg Curl", "Machine leg curl", MuscleGroup.LEGS, ExerciseType.ISOLATION),
            createExercise("Leg Extension", "Machine leg extension", MuscleGroup.LEGS, ExerciseType.ISOLATION),
            createExercise("Calf Raise", "Standing calf raise", MuscleGroup.LEGS, ExerciseType.ISOLATION),
            createExercise("Seated Calf Raise", "Seated calf raise", MuscleGroup.LEGS, ExerciseType.ISOLATION),
            createExercise("Lunges", "Dumbbell lunges", MuscleGroup.LEGS, ExerciseType.COMPOUND),
            createExercise("Bulgarian Split Squat", "Dumbbell Bulgarian split squat", MuscleGroup.LEGS, ExerciseType.COMPOUND),

            // Core exercises
            createExercise("Plank", "Standard plank hold", MuscleGroup.CORE, ExerciseType.BODYWEIGHT),
            createExercise("Crunches", "Standard crunches", MuscleGroup.CORE, ExerciseType.BODYWEIGHT),
            createExercise("Russian Twist", "Weighted Russian twist", MuscleGroup.CORE, ExerciseType.BODYWEIGHT),
            createExercise("Leg Raise", "Hanging leg raise", MuscleGroup.CORE, ExerciseType.BODYWEIGHT),
            createExercise("Cable Woodchop", "Cable woodchop", MuscleGroup.CORE, ExerciseType.COMPOUND),
            createExercise("Ab Rollout", "Ab wheel rollout", MuscleGroup.CORE, ExerciseType.BODYWEIGHT),

            // Full Body exercises
            createExercise("Clean and Press", "Barbell clean and press", MuscleGroup.FULL_BODY, ExerciseType.COMPOUND),
            createExercise("Thruster", "Barbell thruster", MuscleGroup.FULL_BODY, ExerciseType.COMPOUND),
            createExercise("Burpees", "Standard burpees", MuscleGroup.FULL_BODY, ExerciseType.CARDIO),
            createExercise("Kettlebell Swing", "Russian kettlebell swing", MuscleGroup.FULL_BODY, ExerciseType.COMPOUND)
        );

        exerciseRepository.saveAll(exercises);
    }

    private Exercise createExercise(String name, String description, MuscleGroup muscleGroup, ExerciseType exerciseType) {
        Exercise exercise = new Exercise();
        exercise.setName(name);
        exercise.setDescription(description);
        exercise.setMuscleGroup(muscleGroup);
        exercise.setExerciseType(exerciseType);
        return exercise;
    }
}
