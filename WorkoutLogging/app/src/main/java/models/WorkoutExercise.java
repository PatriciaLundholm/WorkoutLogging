package models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class WorkoutExercise {
    @PrimaryKey(autoGenerate = true)

    public int id;
    public int workoutId;
    public int exerciseId;
    public int orderIndex;
}
