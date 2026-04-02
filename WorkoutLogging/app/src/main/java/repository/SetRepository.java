package repository;
import android.content.Context;
import androidx.room.Room;
import database.AppDatabase;
import models.Set;

public class SetRepository {

    private AppDatabase db;

    public SetRepository(Context context) {

        db = Room.databaseBuilder(
                context,
                AppDatabase.class,
                "workout-database"
        ).fallbackToDestructiveMigration().build();
    }

    public void insertSet(Set set) {

        new Thread(() -> {
            db.setDao().insert(set);
        }).start();
    }
}
