package mg.itu.m1p10android.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import androidx.room.Upsert;

import mg.itu.m1p10android.data.ArticleRepository;
import mg.itu.m1p10android.data.models.Article;

@Dao
public abstract class ArticleDao implements ArticleRepository {

    @Query("select id, titre, descr, date_pub, date_modif from articles")
    public abstract Article[] getAll();

    @Query("select id, titre, descr, date_pub, date_modif from articles where '%' || :titre || '%'")
    public abstract Article[] getByTitre(String titre);

    @Query("select id, titre, descr, contenu, video, date_pub, date_modif, longitude, latitude from articles where id = :id")
    public abstract Article getById(Integer id);

    @Query("Select * from articles")
    public abstract Article[] getAllFull();

    @Override
    public void fetchAllFull(OnArrayAction action) {
        LocalDb.dbReadExecutor.execute(() -> {
            Article[] articles = getAllFull();
            action.doAction(articles);
        });
    }
    @Override
    public void fetchAll(OnArrayAction action) {
        LocalDb.dbReadExecutor.execute(() -> {
            Article[] articles = getAll();
            action.doAction(articles);
        });
    }

    @Override
    public void fetchById(int id, OnObjectAction action) {

        LocalDb.dbReadExecutor.execute(() -> {
            Article article = getById(id);
            action.doAction(article);
        });
    }

    @Override
    public void findByText(String searchStr, OnArrayAction action) {
        LocalDb.dbReadExecutor.execute(() -> {
            Article[] articles = getByTitre(searchStr);
            action.doAction(articles);
        });

    }

    @Insert
    public abstract void insertBatch(Article[] articles);

    @Update
    public abstract void updateBatch(Article[] a);

    @Upsert
    public abstract void upsertBatch(Article[] a);
}
