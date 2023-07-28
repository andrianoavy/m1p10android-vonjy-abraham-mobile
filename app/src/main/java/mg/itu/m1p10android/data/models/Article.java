package mg.itu.m1p10android.data.models;

import androidx.annotation.NonNull;

import java.time.LocalDate;
import java.util.ArrayList;

public class Article {
    private Integer id = null;
    private String titre;
    private String descr;

    private LocalDate datePub;
    private LocalDate dateModif;

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

}
