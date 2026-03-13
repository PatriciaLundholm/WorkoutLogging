package database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import models.Exercise;

@Dao
public interface ExerciseDao {
    @Insert
    void insert(Exercise exercise);

    @Query("SELECT * FROM Exercise ORDER BY name ASC")
    List<Exercise> getAllExercises();

    @Query("SELECT * FROM Exercise WHERE name = :name LIMIT 1")
    Exercise findByName(String name);

}
