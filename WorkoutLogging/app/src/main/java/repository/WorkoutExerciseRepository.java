package repository;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

import database.AppDatabase;
import models.Exercise;
import models.WorkoutExercise;

public class WorkoutExerciseRepository {

    private AppDatabase db;

    public WorkoutExerciseRepository(Context context) {
        db = Room.databaseBuilder(
                context,
                AppDatabase.class,
                "workout-database"
        ).fallbackToDestructiveMigration().build();
    }

    public void insert(WorkoutExercise we) {
        new Thread(() -> {
            db.workoutExerciseDao().insert(we);
        }).start();
    }

    public List<Exercise> getExercisesForWorkout(int workoutId) {
        return db.workoutExerciseDao().getExercisesForWorkout(workoutId);
    }
}
