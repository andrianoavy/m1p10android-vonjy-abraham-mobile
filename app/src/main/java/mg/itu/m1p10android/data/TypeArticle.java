package mg.itu.m1p10android.data;

public enum TypeArticle {
    DESTINATION("Destination"),
    EVENEMENT("Evenement");

    public final String label;
    TypeArticle(String label) {
        this.label = label;
    }
}
