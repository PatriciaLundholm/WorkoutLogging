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
            input.setHint("Övningar");

            layout.addView(spinner);
            layout.addView(input);

            new AlertDialog.Builder(this)
                    .setTitle("Lägg till övning")
                    .setView(layout)
                    .setPositiveButton("Lägg till", (dialog, which) -> {

                        String name = input.getText().toString();
                        String group = spinner.getSelectedItem().toString();

                        if (name.isEmpty()) return;

                        Exercise exercise = new Exercise();
                        exercise.name = name;
                        exercise.muscleGroup = group;


                        exerciseRepository.insertExercise(exercise);


                        selectedExercises.add(exercise);


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

        btnStart.setOnClickListener(v -> {

            String name = inputName.getText().toString();

            if (name.isEmpty()) {
                name = "Workout";
            }

            Workout workout = new Workout();
            workout.name = name;


            workoutRepository.insertWorkout(workout);


            new Thread(() -> {
                try {
                    Thread.sleep(500); // liten delay


                    int workoutId = workoutRepository.getLastWorkoutId();


                    for (Exercise e : selectedExercises) {

                        WorkoutExercise we = new WorkoutExercise();
                        we.workoutId = workoutId;
                        we.exerciseId = e.id;

                        workoutExerciseRepository.insert(we);
                    }

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();


            Intent intent = new Intent(this, LogWorkoutActivity.class);
            startActivity(intent);
        });
    }
}