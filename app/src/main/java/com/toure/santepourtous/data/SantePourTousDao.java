package com.toure.santepourtous.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Toure Nathan on 7/5/2018.
 */

@Dao
public interface SantePourTousDao {

    @Query("SELECT * FROM santepourtous ORDER BY titre DESC")
    LiveData<List<SantePourTous>> getAllItems();

    @Query("SELECT * FROM santepourtous WHERE type = 1 ORDER BY titre DESC")
    LiveData<List<SantePourTous>> getAllMaladieItems();

    @Query("SELECT * FROM santepourtous WHERE type = 2 ORDER BY titre DESC")
    LiveData<List<SantePourTous>> getAllBienEtreItems();

    @Query("SELECT * FROM santepourtous WHERE  firebase_id LIKE :firebasekey")
    LiveData<SantePourTous> getMaladieItemByFirebaseId(String firebasekey);

    @Query("SELECT * FROM santepourtous WHERE  id = :id")
    LiveData<SantePourTous> getMaladieItemById(int id);

    @Insert
    void insertAll(List<SantePourTous> items);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(SantePourTous item);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(SantePourTous item);

    @Delete
    void delete(SantePourTous item);
}
