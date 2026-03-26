package com.fitness.service;

import com.fitness.model.WorkoutEntry;
import com.fitness.model.WorkoutEntry.GripType;
import com.fitness.repository.WorkoutEntryRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkoutEntryService {

    private final WorkoutEntryRepository workoutEntryRepository;

    public WorkoutEntry createEntry(WorkoutEntry entry) {
        return workoutEntryRepository.save(entry);
    }

    @Transactional(readOnly = true)
    public WorkoutEntry getEntryById(Long id) {
        return workoutEntryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Workout entry not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<WorkoutEntry> getEntriesByWorkoutId(Long workoutId) {
        return workoutEntryRepository.findByWorkoutIdOrderBySetNumber(workoutId);
    }

    @Transactional(readOnly = true)
    public List<WorkoutEntry> getEntriesByExerciseId(Long exerciseId) {
        return workoutEntryRepository.findByExerciseIdOrderByDateDesc(exerciseId);
    }

    @Transactional(readOnly = true)
    public List<WorkoutEntry> getEntriesByGrip(GripType grip) {
        return workoutEntryRepository.findByGrip(grip);
    }

    @Transactional(readOnly = true)
    public List<GripType> getDistinctGripsByExerciseId(Long exerciseId) {
        return workoutEntryRepository.findDistinctGripsByExerciseId(exerciseId);
    }

    public WorkoutEntry updateEntry(Long id, WorkoutEntry entryDetails) {
        WorkoutEntry entry = getEntryById(id);
        
        entry.setSetNumber(entryDetails.getSetNumber());
        entry.setRepetitions(entryDetails.getRepetitions());
        entry.setWeight(entryDetails.getWeight());
        entry.setGrip(entryDetails.getGrip());
        entry.setNotes(entryDetails.getNotes());
        
        return workoutEntryRepository.save(entry);
    }

    public void deleteEntry(Long id) {
        WorkoutEntry entry = getEntryById(id);
        workoutEntryRepository.delete(entry);
    }

    @Transactional(readOnly = true)
    public long getEntryCount() {
        return workoutEntryRepository.count();
    }

    @Transactional(readOnly = true)
    public long getEntryCountByExerciseId(Long exerciseId) {
        return workoutEntryRepository.findByExerciseId(exerciseId).size();
    }
}
