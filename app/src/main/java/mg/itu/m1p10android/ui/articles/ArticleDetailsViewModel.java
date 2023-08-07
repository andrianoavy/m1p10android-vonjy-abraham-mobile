package mg.itu.m1p10android.ui.articles;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import mg.itu.m1p10android.data.ArticleRepository;
import mg.itu.m1p10android.data.http.ArticleHttp;
import mg.itu.m1p10android.data.local.ArticleDao;
import mg.itu.m1p10android.data.local.LocalDb;
import mg.itu.m1p10android.models.MyApp;

public class ArticleDetailsViewModel extends AndroidViewModel {
    private ArticleRepository repo;
    private final ArticleHttp api;
    private final ArticleDao dao;

    public ArticleDetailsViewModel(@NonNull Application application) {
        super(application);
        api = new ArticleHttp(application);
        dao = LocalDb.getDatabase(application).articleDao();
        repo = MyApp.getRepo(application, api, dao);
    }

    public void fetchById(int id, ArticleRepository.OnObjectAction action){
        repo.fetchById(id, action);
    }


}
