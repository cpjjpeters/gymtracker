# Fitness Tracker API

A Spring Boot REST API for tracking gym exercises, workouts, and progress. Track exercises, repetitions, weight, and grip types.

## Features

- **Exercise Management**: Create, read, update, and delete exercises with muscle group and type categorization
- **Workout Tracking**: Log workout sessions with date, duration, and notes
- **Workout Entries**: Track individual sets with repetitions, weight, and grip type
- **Basic Exercise Dataset**: Pre-loaded with 50+ common gym exercises
- **Validation**: Input validation with detailed error messages
- **Error Handling**: Global exception handling for consistent API responses

## Tech Stack

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- H2 Database (in-memory)
- Maven
- Lombok

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6+

### Running the Application

1. Clone the repository:
```bash
git clone <repository-url>
cd fitness-tracker
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

### H2 Database Console

Access the H2 database console at: `http://localhost:8080/h2-console`

- JDBC URL: `jdbc:h2:mem:fitnessdb`
- Username: `sa`
- Password: (leave empty)

## API Endpoints

### Exercises

#### Get All Exercises
```
GET /api/exercises
```

#### Get Exercise by ID
```
GET /api/exercises/{id}
```

#### Get Exercise by Name
```
GET /api/exercises/name/{name}
```

#### Get Exercises by Muscle Group
```
GET /api/exercises/muscle-group/{muscleGroup}
```
Muscle Groups: `CHEST`, `BACK`, `SHOULDERS`, `BICEPS`, `TRICEPS`, `LEGS`, `CORE`, `FULL_BODY`

#### Get Exercises by Type
```
GET /api/exercises/type/{exerciseType}
```
Exercise Types: `COMPOUND`, `ISOLATION`, `BODYWEIGHT`, `CARDIO`

#### Filter Exercises by Muscle Group and Type
```
GET /api/exercises/filter?muscleGroup={muscleGroup}&exerciseType={exerciseType}
```

#### Create Exercise
```
POST /api/exercises
Content-Type: application/json

{
  "name": "Bench Press",
  "description": "Flat barbell bench press",
  "muscleGroup": "CHEST",
  "exerciseType": "COMPOUND"
}
```

#### Update Exercise
```
PUT /api/exercises/{id}
Content-Type: application/json

{
  "name": "Updated Exercise Name",
  "description": "Updated description",
  "muscleGroup": "CHEST",
  "exerciseType": "COMPOUND"
}
```

#### Delete Exercise
```
DELETE /api/exercises/{id}
```

#### Check if Exercise Exists
```
GET /api/exercises/exists/{name}
```

### Workouts

#### Get All Workouts
```
GET /api/workouts
```

#### Get Workout by ID
```
GET /api/workouts/{id}
```

#### Get Workouts by Date
```
GET /api/workouts/date/{date}
```
Date format: `YYYY-MM-DD` (e.g., `2024-03-26`)

#### Get Workouts by Date Range
```
GET /api/workouts/date-range?startDate={startDate}&endDate={endDate}
```

#### Create Workout
```
POST /api/workouts
Content-Type: application/json

{
  "date": "2024-03-26",
  "notes": "Chest and triceps day",
  "durationMinutes": 60
}
```

#### Update Workout
```
PUT /api/workouts/{id}
Content-Type: application/json

{
  "date": "2024-03-26",
  "notes": "Updated notes",
  "durationMinutes": 75
}
```

#### Delete Workout
```
DELETE /api/workouts/{id}
```

#### Add Entry to Workout
```
POST /api/workouts/{workoutId}/entries
Content-Type: application/json

{
  "exercise": {
    "id": 1
  },
  "setNumber": 1,
  "repetitions": 10,
  "weight": 135.00,
  "grip": "OVERHAND",
  "notes": "Felt strong"
}
```

#### Remove Entry from Workout
```
DELETE /api/workouts/{workoutId}/entries/{entryId}
```

#### Get Workout Count
```
GET /api/workouts/count
```

### Workout Entries

#### Get Entry by ID
```
GET /api/entries/{id}
```

#### Get Entries by Workout ID
```
GET /api/entries/workout/{workoutId}
```

#### Get Entries by Exercise ID
```
GET /api/entries/exercise/{exerciseId}
```

#### Get Entries by Grip Type
```
GET /api/entries/grip/{grip}
```
Grip Types: `OVERHAND`, `UNDERHAND`, `NEUTRAL`, `MIXED`, `WIDE`, `NARROW`, `CLOSE`, `HOOK`, `NONE`

#### Get Distinct Grips for Exercise
```
GET /api/entries/exercise/{exerciseId}/grips
```

#### Create Entry
```
POST /api/entries
Content-Type: application/json

{
  "workout": {
    "id": 1
  },
  "exercise": {
    "id": 1
  },
  "setNumber": 1,
  "repetitions": 10,
  "weight": 135.00,
  "grip": "OVERHAND",
  "notes": "Felt strong"
}
```

#### Update Entry
```
PUT /api/entries/{id}
Content-Type: application/json

{
  "setNumber": 1,
  "repetitions": 12,
  "weight": 145.00,
  "grip": "OVERHAND",
  "notes": "Increased weight"
}
```

#### Delete Entry
```
DELETE /api/entries/{id}
```

#### Get Entry Count
```
GET /api/entries/count
```

#### Get Entry Count by Exercise
```
GET /api/entries/exercise/{exerciseId}/count
```

## Data Models

### Exercise
- `id`: Long (auto-generated)
- `name`: String (required, unique, max 100 characters)
- `description`: String (max 500 characters)
- `muscleGroup`: Enum (CHEST, BACK, SHOULDERS, BICEPS, TRICEPS, LEGS, CORE, FULL_BODY)
- `exerciseType`: Enum (COMPOUND, ISOLATION, BODYWEIGHT, CARDIO)
- `createdAt`: LocalDateTime (auto-generated)
- `updatedAt`: LocalDateTime (auto-updated)

### Workout
- `id`: Long (auto-generated)
- `date`: LocalDate (required)
- `notes`: String (max 500 characters)
- `durationMinutes`: Integer
- `entries`: List<WorkoutEntry>
- `createdAt`: LocalDateTime (auto-generated)
- `updatedAt`: LocalDateTime (auto-updated)

### WorkoutEntry
- `id`: Long (auto-generated)
- `workout`: Workout (required)
- `exercise`: Exercise (required)
- `setNumber`: Integer (required, min 1)
- `repetitions`: Integer (required, min 0)
- `weight`: BigDecimal (min 0)
- `grip`: Enum (OVERHAND, UNDERHAND, NEUTRAL, MIXED, WIDE, NARROW, CLOSE, HOOK, NONE)
- `notes`: String (max 200 characters)
- `createdAt`: LocalDateTime (auto-generated)
- `updatedAt`: LocalDateTime (auto-updated)

## Pre-loaded Exercises

The application comes with 50+ pre-loaded exercises across all muscle groups:

### Chest
- Bench Press, Incline Bench Press, Decline Bench Press
- Dumbbell Press, Incline Dumbbell Press
- Cable Flyes, Dumbbell Flyes
- Push-ups

### Back
- Deadlift, Barbell Row, Dumbbell Row
- Lat Pulldown, Pull-ups, Seated Cable Row
- Face Pulls

### Shoulders
- Overhead Press, Dumbbell Shoulder Press
- Lateral Raises, Front Raises, Rear Delt Flyes
- Arnold Press

### Biceps
- Barbell Curl, Dumbbell Curl, Hammer Curl
- Preacher Curl, Cable Curl, Concentration Curl

### Triceps
- Close-Grip Bench Press, Tricep Pushdown
- Overhead Tricep Extension, Skull Crushers
- Dips, Tricep Kickback

### Legs
- Squat, Front Squat, Leg Press
- Romanian Deadlift, Leg Curl, Leg Extension
- Calf Raise, Seated Calf Raise
- Lunges, Bulgarian Split Squat

### Core
- Plank, Crunches, Russian Twist
- Leg Raise, Cable Woodchop, Ab Rollout

### Full Body
- Clean and Press, Thruster
- Burpees, Kettlebell Swing

## Error Handling

The API returns consistent error responses:

### 400 Bad Request
```json
{
  "status": 400,
  "error": "Bad Request",
  "message": "Invalid input data",
  "timestamp": "2024-03-26T10:30:00"
}
```

### 404 Not Found
```json
{
  "status": 404,
  "error": "Not Found",
  "message": "Exercise not found with id: 999",
  "timestamp": "2024-03-26T10:30:00"
}
```

### 400 Validation Error
```json
{
  "status": 400,
  "error": "Validation Failed",
  "message": "Input validation failed",
  "timestamp": "2024-03-26T10:30:00",
  "fieldErrors": {
    "name": "Exercise name is required",
    "muscleGroup": "Muscle group is required"
  }
}
```

## CORS

The API supports CORS and allows requests from any origin.

## License

This project is open source and available under the MIT License.
# gymtracker
