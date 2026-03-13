package database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import models.Exercise;
import models.Set;
import models.Workout;
import models.WorkoutExercise;

@Database(
        entities = {Workout.class, Exercise.class, WorkoutExercise.class, Set.class},
        version = 1
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract WorkoutDao workoutDao();
    public abstract ExerciseDao exerciseDao();
    public abstract SetDao setDao();
    public abstract WorkoutExerciseDao workoutExerciseDao();
}
