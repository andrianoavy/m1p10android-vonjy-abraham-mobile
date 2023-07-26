package mg.itu.m1p10android.data;

import java.util.ArrayList;

public class Article {
    private Long id = null;
    private String titre;
    private String descr;
    private TypeArticle type;

    public Article() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDescr() {
        return descr;
    }

    public void setDescr(String descr) {
        this.descr = descr;
    }

    public TypeArticle getType() {
        return type;
    }

    public void setType(TypeArticle typeArticle) {
        this.type = typeArticle;
    }

    public static Article mockSingle(Long i){

        Article article = new Article();
        article.setId(i);
        article.setTitre("Title" + i); // Append id to title to make it unique
        article.setDescr("Description" + i); // Append id to description to make it unique
        article.setType(TypeArticle.EVENEMENT);
        return article;

    }
    public static ArrayList<Article> mock(){
        // Create ArrayList of Article
        ArrayList<Article> articles = new ArrayList<>();

        // Define two Strings for title and description
        String title = "Title";
        String description = "Description";

        // Generate 10 mock Article objects of type EVENEMENT
        for(long i = 1; i <= 10; i++) {
            Article article = new Article();
            article.setId(i);
            article.setTitre(title + i); // Append id to title to make it unique
            article.setDescr(description + i); // Append id to description to make it unique
            article.setType(TypeArticle.EVENEMENT);

            // Add article to articles ArrayList
            articles.add(article);
        }

        // Generate remaining 15 mock Article objects of type DESTINATION
        for(long i = 11; i <= 25; i++) {
            Article article = new Article();
            article.setId(i);
            article.setTitre(title + i); // Append id to title to make it unique
            article.setDescr(description + i); // Append id to description to make it unique
            article.setType(TypeArticle.DESTINATION);

            // Add article to articles ArrayList
            articles.add(article);
        }
        return articles;
    }
}
