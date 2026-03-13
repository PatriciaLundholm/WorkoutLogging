package activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.workoutlogging.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newWorkout = findViewById(R.id.btnNewWorkout);
        Button history = findViewById(R.id.btnHistory);
        Button progress = findViewById(R.id.btnProgress);


        newWorkout.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, CreateWorkoutActivity.class);
            startActivity(intent);
        });

        history.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, WorkoutListActivity.class);
            startActivity(intent);
        });

        progress.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ProgressActivity.class);
            startActivity(intent);
        });

    }
}