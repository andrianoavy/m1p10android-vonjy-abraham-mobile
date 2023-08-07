package mg.itu.m1p10android.data.models;

import android.text.Html;
import android.text.Spanned;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity(tableName = "articles")
public class Article {
    @PrimaryKey
    private Integer id = null;
    private String titre;
    private String descr;

    public String getDate_pub() {
        return date_pub;
    }

    public void setDate_pub(String date_pub) {
        this.date_pub = date_pub;
    }

    public String getDate_modif() {
        return date_modif;
    }

    public void setDate_modif(String date_modif) {
        this.date_modif = date_modif;
    }

    private String date_pub;
    private String date_modif;
    @Ignore
    private LocalDateTime datePub;
    @Ignore
    private LocalDateTime dateModif;
    private String contenu;
    private String video;

    private Double longitude;
    private Double latitude;

    public Article() {
    }

    @NonNull
    @Override
    public String toString() {
        return "{" + titre + " | " + descr + "}";
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

    public LocalDateTime getDatePub() {
        if(datePub == null){
            datePub = LocalDateTime.parse(date_pub.replace(" ","T"));
        }
        return datePub;
    }

    public void setDatePub(LocalDateTime datePub) {
        this.datePub = datePub;
    }

    public LocalDateTime getDateModif() {
        if(dateModif == null){
            dateModif = LocalDateTime.parse(date_modif.replace(" ","T"));
        }
        return dateModif;
    }

    public void setDateModif(LocalDateTime dateModif) {
        this.dateModif = dateModif;
    }

    public String getContenu() {
        if (contenu == null || contenu.isEmpty()) {
            return "<span></span>";
        }
        return contenu;
    }

    public Spanned getHtmlContenu() {
        if (this.contenu == null) {
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

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
