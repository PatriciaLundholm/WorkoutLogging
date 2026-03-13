package models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Set {
    @PrimaryKey(autoGenerate = true)

    public int id;
    public int workoutExerciseId;
    public int weight;
    public int reps;
    public int setNumber;
}
