package com.toure.santepourtous.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by Toure Nathan on 7/5/2018.
 */

@Entity(indices = {@Index(value = {"firebase_id"}, unique = true)})
public class SantePourTous {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;

    private String titre;
    private String astuce;
    private String conseille;
    private Long type;

    @NonNull
    @ColumnInfo(name = "firebase_id")
    private String firebaseId;

    public SantePourTous() {
    }

    public SantePourTous(int id, String titre, String astuce, String conseille, long type) {
        this.id = id;
        this.titre = titre;
        this.astuce = astuce;
        this.type = type;
        this.conseille = conseille;
    }

    @Ignore
    public SantePourTous(String titre, String astuce, long type) {
        this.titre = titre;
        this.astuce = astuce;
        this.type = type;
    }

    @Ignore
    public SantePourTous(String titre, String astuce, String conseille, long type) {
        this.titre = titre;
        this.astuce = astuce;
        this.type = type;
        this.conseille = conseille;
    }

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getAstuce() {
        return astuce;
    }

    public void setAstuce(String astuce) {
        this.astuce = astuce;
    }

    public String getConseille() {
        return conseille;
    }

    public void setConseille(String conseille) {
        this.conseille = conseille;
    }

    public Long getType() {
        return type;
    }

    public void setType(Long type) {
        this.type = type;
    }

    public String getFirebaseId() {
        return firebaseId;
    }

    public void setFirebaseId(@NonNull String firebaseId) {
        this.firebaseId = firebaseId;
    }
}