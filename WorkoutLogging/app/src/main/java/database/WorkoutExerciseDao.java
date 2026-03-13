package database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import models.WorkoutExercise;

@Dao
public interface WorkoutExerciseDao {

    @Insert
    void insert(WorkoutExercise workoutExercise);

    @Query("SELECT * FROM WorkoutExercise WHERE workoutId = :workoutId")
    List<WorkoutExercise> getExercisesForWorkout(int workoutId);

}
