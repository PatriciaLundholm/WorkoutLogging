package com.example.workoutlogging.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.workoutlogging.R;

import java.util.ArrayList;
import java.util.List;

import models.Exercise;
import models.Workout;
import models.WorkoutExercise;
import repository.ExerciseRepository;
import repository.WorkoutExerciseRepository;
import repository.WorkoutRepository;

public class CreateWorkoutActivity extends AppCompatActivity {

    private WorkoutRepository workoutRepository;
    private ExerciseRepository exerciseRepository;
    private WorkoutExerciseRepository workoutExerciseRepository;

    private LinearLayout exerciseContainer;
    private List<Exercise> selectedExercises = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_workout);

        EditText inputName = findViewById(R.id.inputWorkoutName);
        Button btnStart = findViewById(R.id.btnStartWorkout);
        Button btnAddExercise = findViewById(R.id.btnAddExercise);

        exerciseContainer = findViewById(R.id.exerciseContainer);

        workoutRepository = new WorkoutRepository(getApplicationContext());
        exerciseRepository = new ExerciseRepository(getApplicationContext());
        workoutExerciseRepository = new WorkoutExerciseRepository(getApplicationContext());

        // 🔥 ADD EXERCISE
        btnAddExercise.setOnClickListener(v -> {

            String[] groups = {"Bröst", "Rygg", "Ben", "Axlar", "Armar"};

            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);

            Spinner spinner = new Spinner(this);

            ArrayAdapter<String> adapter = new ArrayAdapter<>(
                    this,
                    android.R.layout.simple_spinner_dropdown_item,
                    groups
            );

            spinner.setAdapter(adapter);

            EditText input = new EditText(this);
            input.setHint("Övning");

            layout.addView(spinner);
            layout.addView(input);

            new AlertDialog.Builder(this)
                    .setTitle("Lägg till övning")
                    .setView(layout)
                    .setPositiveButton("Lägg till", (dialog, which) -> {

                        String name = input.getText().toString();
                        String group = spinner.getSelectedItem().toString();

                        if (name.isEmpty()) return;

                        // 🔥 skapa exercise
                        Exercise exercise = new Exercise();
                        exercise.name = name;
                        exercise.muscleGroup = group;

                        // 🔥 spara i databas
                        exerciseRepository.insertExercise(exercise);

                        // 🔥 lägg till i lista
                        selectedExercises.add(exercise);

                        // 🔥 visa i UI
                        TextView tv = new TextView(this);
                        tv.setText(name + " (" + group + ")");
                        tv.setTextColor(getResources().getColor(android.R.color.white));
                        tv.setTextSize(16);
                        tv.setPadding(0, 16, 0, 16);

                        exerciseContainer.addView(tv);

                    })
                    .setNegativeButton("Avbryt", null)
                    .show();
        });

        // 🔥 START WORKOUT
        btnStart.setOnClickListener(v -> {

            String name = inputName.getText().toString();

            if (name.isEmpty()) {
                name = "Workout";
            }

            Workout workout = new Workout();
            workout.name = name;

            // 🔥 spara workout
            workoutRepository.insertWorkout(workout);

            new Thread(() -> {
                try {
                    Thread.sleep(500);

                    int workoutId = workoutRepository.getLastWorkoutId();

                    // 🔥 koppla exercises till workout
                    for (Exercise e : selectedExercises) {
                        WorkoutExercise we = new WorkoutExercise();
                        we.workoutId = workoutId;
                        we.exerciseId = e.id;

                        workoutExerciseRepository.insert(we);
                    }

                    // 🔥 gå till nästa activity
                    runOnUiThread(() -> {
                        Intent intent = new Intent(this, LogWorkoutActivity.class);
                        intent.putExtra("WORKOUT_ID", workoutId);
                        startActivity(intent);
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();

        });
    }
}