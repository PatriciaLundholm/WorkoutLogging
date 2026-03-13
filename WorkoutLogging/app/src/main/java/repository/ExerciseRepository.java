package repository;

import android.content.Context;

import androidx.room.Room;

import java.util.List;

import database.AppDatabase;
import models.Exercise;

public class ExerciseRepository {

    private AppDatabase db;
    public ExerciseRepository(Context context) {
        db = Room.databaseBuilder(
                context,
                AppDatabase.class,
                "workout-database"
        ).build();
    }

    public List<Exercise> getAllExercises() {
        return db.exerciseDao().getAllExercises();
    }

    public void insertExercise(Exercise exercise) {
        new Thread(() -> {
            db.exerciseDao().insert(exercise);
        }).start();
    }
}
