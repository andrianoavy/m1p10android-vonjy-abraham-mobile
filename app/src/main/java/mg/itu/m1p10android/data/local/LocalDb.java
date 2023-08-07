package mg.itu.m1p10android.data.local;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import mg.itu.m1p10android.data.models.Article;


@Database(entities = { Article.class}, version = 1,exportSchema = false )
public abstract class LocalDb extends RoomDatabase {
    //Put Dao here
    public abstract ArticleDao articleDao();

    private static volatile LocalDb INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService dbWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static final ExecutorService dbReadExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static LocalDb getDatabase(final Context context){
        if (INSTANCE == null){
            synchronized (LocalDb.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    LocalDb.class,
                                    "tourismo")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
