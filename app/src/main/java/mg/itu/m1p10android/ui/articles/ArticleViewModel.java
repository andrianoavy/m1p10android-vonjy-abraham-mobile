package mg.itu.m1p10android.ui.articles;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import mg.itu.m1p10android.data.http.ArticleHttp;

public class ArticleViewModel extends AndroidViewModel {

    private final ArticleHttp api;
    public ArticleViewModel(@NonNull Application application) {
        super(application);
        api = new ArticleHttp(application);
    }

    public void fetchAll(ArticleHttp.OnResponseArrayAction action){
        api.fetchAll(action);
    }

    public void search(String searchStr, ArticleHttp.OnResponseArrayAction action){
        api.findByText(searchStr, action);
    }
}

