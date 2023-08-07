package mg.itu.m1p10android.ui.articles;

import android.app.Application;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import mg.itu.m1p10android.data.ArticleRepository;
import mg.itu.m1p10android.data.http.ArticleHttp;
import mg.itu.m1p10android.data.local.ArticleDao;
import mg.itu.m1p10android.data.local.LocalDb;
import mg.itu.m1p10android.models.MyApp;

public class ArticleViewModel extends AndroidViewModel {

    private final ArticleHttp api;
    private final ArticleDao dao;
    private final ArticleRepository repo;


    public ArticleViewModel(@NonNull Application application) {
        super(application);

        api = new ArticleHttp(application);
        dao = LocalDb.getDatabase(application).articleDao();
        repo = MyApp.getRepo(application, api, dao);
    }

    public void fetchAll(ArticleRepository.OnArrayAction action) {
        repo.fetchAll(action);
    }


    public void search(String searchStr, ArticleRepository.OnArrayAction action) {
        repo.findByText(searchStr, action);
    }

    public void writeToLocal() {
        if(!MyApp.isConnected(getApplication())){
            Toast.makeText(getApplication(), "Pas de connexion Internet! :(", Toast.LENGTH_SHORT).show();
            return;
        }
        api.fetchAllFull(response -> {
            LocalDb.dbWriteExecutor.execute(() -> {
                dao.upsertBatch(response);
            });
            Toast.makeText(getApplication(), "Téléchargement des articles terminé", Toast.LENGTH_SHORT).show();
        });
        Toast.makeText(getApplication(), "Téléchargement des articles en cours...", Toast.LENGTH_SHORT).show();

    }
}

