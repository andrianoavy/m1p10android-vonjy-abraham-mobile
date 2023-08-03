package mg.itu.m1p10android.data.models;

import android.text.Html;
import android.text.Spanned;

import androidx.annotation.NonNull;

import java.time.LocalDate;

public class Article {
    private Integer id = null;
    private String titre;
    private String descr;

    private LocalDate datePub;
    private LocalDate dateModif;

    private String contenu;
    private String video;

    public Article() {
    }

    @NonNull
    @Override
    public String toString() {
        return "{"+titre+" | "+descr+"}";
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public LocalDate getDatePub() {
        return datePub;
    }

    public void setDatePub(LocalDate datePub) {
        this.datePub = datePub;
    }

    public LocalDate getDateModif() {
        return dateModif;
    }

    public void setDateModif(LocalDate dateModif) {
        this.dateModif = dateModif;
    }

    public String getContenu() {
        if(contenu == null || contenu.isEmpty()) {
            return "<span></span>";
        }
        return contenu;
    }

    public Spanned getHtmlContenu() {
        if(this.contenu == null){
            this.contenu = "";
        }
        return Html.fromHtml(this.contenu, Html.FROM_HTML_MODE_LEGACY);
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
