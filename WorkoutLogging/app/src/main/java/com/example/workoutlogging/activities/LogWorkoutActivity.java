package com.example.workoutlogging.activities;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workoutlogging.R;

import java.util.List;

import models.Exercise;
import repository.WorkoutExerciseRepository;

public class LogWorkoutActivity extends AppCompatActivity {

    private LinearLayout exerciseContainer;
    private WorkoutExerciseRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_workout);

        // 🔥 Hämta workoutId från intent
        int workoutId = getIntent().getIntExtra("WORKOUT_ID", -1);

        exerciseContainer = findViewById(R.id.exerciseContainer);

        repository = new WorkoutExerciseRepository(getApplicationContext());

        // 🔥 Hämta exercises (kör i thread)
        new Thread(() -> {

            List<Exercise> exercises = repository.getExercisesForWorkout(workoutId);

            runOnUiThread(() -> {

                // 🔥 Loopar igenom alla exercises
                for (Exercise e : exercises) {

                    // 📌 namn på övning
                    TextView name = new TextView(this);
                    name.setText(e.name);
                    name.setTextSize(18);
                    name.setTextColor(getResources().getColor(android.R.color.white));

                    // 📌 input för vikt
                    EditText weight = new EditText(this);
                    weight.setHint("kg");

                    // 📌 input för reps
                    EditText reps = new EditText(this);
                    reps.setHint("reps");

                    // 📌 knapp för att lägga till set
                    Button addSet = new Button(this);
                    addSet.setText("+ Add Set");

                    // 📌 layout för varje exercise
                    LinearLayout layout = new LinearLayout(this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setPadding(0, 20, 0, 20);

                    layout.addView(name);
                    layout.addView(weight);
                    layout.addView(reps);
                    layout.addView(addSet);

                    // 🔥 Lägg till i huvudlayout
                    exerciseContainer.addView(layout);

                    // 🔥 Klick på "Add Set"
                    addSet.setOnClickListener(v -> {

                        String w = weight.getText().toString();
                        String r = reps.getText().toString();

                        if (w.isEmpty() || r.isEmpty()) return;

                        Toast.makeText(this,
                                e.name + ": " + w + "kg x " + r,
                                Toast.LENGTH_SHORT).show();

                        // 🔥 (senare) spara i databasen

                        weight.setText("");
                        reps.setText("");
                    });
                }
            });

        }).start();
    }
}