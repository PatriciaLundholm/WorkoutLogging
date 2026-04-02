package database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import models.Set;

@Dao
public interface SetDao {

    @Insert
    void insert(Set set);

    @Query("SELECT * FROM `Set` WHERE exerciseId = :exerciseId")
    List<Set> getSetsForExercise(int exerciseId);

    @Query("SELECT * FROM `Set` WHERE workoutExerciseId = :id ORDER BY setNumber ASC")
    List<Set> getSetsForExercise(int id);

    @Query("SELECT s.* " +
            "FROM `Set` s " +
            "JOIN WorkoutExercise we ON s.workoutExerciseId = we.id " +
            "WHERE we.exerciseId = :exerciseId " +
            "ORDER BY s.weight DESC")
    List<Set> getProgressForExercise(int exerciseId);

    @Query("SELECT s.* " +
            "FROM `Set` s " +
            "JOIN WorkoutExercise we ON s.workoutExerciseId = we.id " +
            "WHERE we.exerciseId = :exerciseId " +
            "AND s.reps = :reps " +
            "ORDER BY s.weight DESC " +
            "LIMIT 1")
    Set getBestForReps(int exerciseId, int reps);
}
