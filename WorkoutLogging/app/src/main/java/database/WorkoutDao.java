package database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import models.Workout;

@Dao
public interface WorkoutDao {

    @Insert
    void insert(Workout workout); //Sparar en workout

    @Query("SELECT * FROM Workout")
    List<Workout> getAllWorkouts(); //hämtar alla pass

    @Delete
    void delete(Workout workout);
}
