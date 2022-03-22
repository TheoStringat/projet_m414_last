package iut.project;

import android.app.AlertDialog;
import android.app.Notification;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.core.app.NotificationCompat;


public class President implements Parcelable {
    private String nom;
    private String statut;
    private int age;
    private String periode;
    private String description;
    private String lien;

    public President(String nom, String statut, int age, String periode) {
        this.nom = nom;
        this.statut = statut;
        this.age = age;
        this.periode = periode;
    }

    public President(String nom, String statut, int age, String periode, String description, String lien) {
        this.nom = nom;
        this.statut = statut;
        this.age = age;
        this.periode = periode;
        this.description = description;
        this.lien = lien;
    }



    /*
        Utilisation d'objet parcelable
     */
    protected President(Parcel in) {
        nom = in.readString();
        statut = in.readString();
        age = in.readInt();
        periode = in.readString();
        description = in.readString();
        lien = in.readString();
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nom);
        dest.writeString(statut);
        dest.writeInt(age);
        dest.writeString(periode);
        dest.writeString(description);
        dest.writeString(lien);
    }




    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<President> CREATOR = new Creator<President>() {
        @Override
        public President createFromParcel(Parcel in) {
            return new President(in);
        }

        @Override
        public President[] newArray(int size) {
            return new President[size];
        }
    };

    public String getNom() {
        return nom;
    }

    public String getStatut() {
        return statut;
    }

    public int getAge() {
        return age;
    }

    public String getPeriode() {
        return periode;
    }

    public String getDescription() {
        return description;
    }

    public String getLien() {
        return lien;
    }

    @Override
    public String toString() {
        return "President{" +
                "nom='" + nom + '\'' +
                ", statut'" + statut + '\'' +
                ", age=" + age +
                ", periode='" + periode + '\'' +
                ", description='" + description +
                '}';
    }

}