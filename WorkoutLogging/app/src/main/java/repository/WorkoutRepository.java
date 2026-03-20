package repository;
import android.content.Context;
import androidx.room.Room;
import java.util.List;
import database.AppDatabase;
import models.Workout;

public class WorkoutRepository {
    private AppDatabase db;

    public WorkoutRepository(Context context) {
        db = Room.databaseBuilder(
                context,
                AppDatabase.class,
                "workout-database"
        ).build();
    }

    public void insertWorkout(Workout workout) {
        new Thread(() -> {
            db.workoutDao().insert(workout);
        }).start();
    }

    public List<Workout> getAllWorkouts() {
        return db.workoutDao().getAllWorkouts();
    }

    public int getLastWorkoutId() {
        return db.workoutDao().getLastWorkoutId();
    }
}

