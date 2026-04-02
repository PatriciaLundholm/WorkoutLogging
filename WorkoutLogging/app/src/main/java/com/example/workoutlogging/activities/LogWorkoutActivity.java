package com.example.workoutlogging.activities;

import android.os.Bundle;
import android.widget.*;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workoutlogging.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Exercise;
import models.Set;
import repository.SetRepository;
import repository.WorkoutExerciseRepository;

public class LogWorkoutActivity extends AppCompatActivity {

    private LinearLayout exerciseContainer;
    private WorkoutExerciseRepository repository;
    private SetRepository setRepository;

    private Map<Integer, Integer> setCounter = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_workout);

        int workoutId = getIntent().getIntExtra("WORKOUT_ID", -1);

        exerciseContainer = findViewById(R.id.exerciseContainer);

        repository = new WorkoutExerciseRepository(getApplicationContext());
        setRepository = new SetRepository(getApplicationContext());

        new Thread(() -> {
            List<Exercise> exercises = repository.getExercisesForWorkout(workoutId);

            runOnUiThread(() -> {

                for (Exercise e : exercises) {

                    setCounter.put(e.id, 1);

                    TextView name = new TextView(this);
                    name.setText(e.name);
                    name.setTextSize(18);
                    name.setTextColor(getResources().getColor(android.R.color.white));

                    EditText weight = new EditText(this);
                    weight.setHint("kg");

                    EditText reps = new EditText(this);
                    reps.setHint("reps");

                    Button addSet = new Button(this);
                    addSet.setText("+ Add Set");

                    LinearLayout layout = new LinearLayout(this);
                    layout.setOrientation(LinearLayout.VERTICAL);
                    layout.setPadding(0, 20, 0, 20);

                    layout.addView(name);
                    layout.addView(weight);
                    layout.addView(reps);
                    layout.addView(addSet);

                    exerciseContainer.addView(layout);

                    addSet.setOnClickListener(v -> {

                        String w = weight.getText().toString();
                        String r = reps.getText().toString();

                        if (w.isEmpty() || r.isEmpty()) return;

                        int currentSet = setCounter.get(e.id);

                        models.Set set = new models.Set();
                        set.exerciseId = e.id;
                        set.weight = Float.parseFloat(w);
                        set.reps = Integer.parseInt(r);
                        set.setNumber = currentSet;

                        setRepository.insertSet(set);

                        setCounter.put(e.id, currentSet + 1);

                        Toast.makeText(this,
                                "Set " + currentSet + ": " + w + "kg x " + r,
                                Toast.LENGTH_SHORT).show();

                        weight.setText("");
                        reps.setText("");
                    });
                }
            });

        }).start();
    }
}