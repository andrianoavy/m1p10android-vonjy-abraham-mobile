package mg.itu.m1p10android.data;

import mg.itu.m1p10android.data.models.Article;

public interface ArticleRepository {
    public void fetchAll(OnArrayAction action);
    public void fetchAllFull(OnArrayAction action);
    public void fetchById(int id, OnObjectAction action);


    public void findByText(String searchStr, OnArrayAction action);


    @FunctionalInterface
    public interface OnArrayAction {
        void doAction(Article[] response);
    }

    @FunctionalInterface
    public interface OnObjectAction {
        void doAction(Article response);
    }
}
