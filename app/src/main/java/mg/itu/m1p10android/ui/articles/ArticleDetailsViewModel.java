package mg.itu.m1p10android.ui.articles;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import mg.itu.m1p10android.data.http.ArticleHttp;

public class ArticleDetailsViewModel extends AndroidViewModel {
    private ArticleHttp api;

    public ArticleDetailsViewModel(@NonNull Application application) {
        super(application);
        api = new ArticleHttp(application);
    }

    public void fetchById(int id, ArticleHttp.OnResponseObjectAction action){
        api.fetchById(id, action);
    }

}
