package database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import models.Exercise;
import models.WorkoutExercise;

@Dao
public interface WorkoutExerciseDao {


    @Insert
    void insert(WorkoutExercise workoutExercise);



    @Query("SELECT * FROM WorkoutExercise WHERE workoutId = :workoutId")
    List<WorkoutExercise> getWorkoutExercises(int workoutId);



    @Query("SELECT e.* FROM Exercise e " +
            "JOIN WorkoutExercise we ON e.id = we.exerciseId " +
            "WHERE we.workoutId = :workoutId")
    List<Exercise> getExercisesForWorkout(int workoutId);



    @Query("DELETE FROM WorkoutExercise WHERE workoutId = :workoutId")
    void deleteByWorkoutId(int workoutId);
}