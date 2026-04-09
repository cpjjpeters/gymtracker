package com.fitness.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "workout_entries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkoutEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_id", nullable = false)
    @JsonIgnore
    private Workout workout;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_id", nullable = false)
    private Exercise exercise;

    @NotNull(message = "Set number is required")
    @Min(value = 1, message = "Set number must be at least 1")
    @Column(name = "set_number", nullable = false)
    private Integer setNumber;

    @NotNull(message = "Repetitions are required")
    @Min(value = 0, message = "Repetitions must be at least 0")
    @Column(nullable = false)
    private Integer repetitions;

    @Min(value = 0, message = "Weight must be at least 0")
    @Column(precision = 10, scale = 2)
    private BigDecimal weight;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GripType grip;

    @Size(max = 200, message = "Notes must not exceed 200 characters")
    @Column(length = 200)
    private String notes;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public enum GripType {
        OVERHAND("Overhand"),
        UNDERHAND("Underhand"),
        NEUTRAL("Neutral"),
        MIXED("Mixed"),
        WIDE("Wide"),
        NARROW("Narrow"),
        CLOSE("Close"),
        HOOK("Hook"),
        NONE("None");

        private final String displayName;

        GripType(String displayName) {
            this.displayName = displayName;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
}
