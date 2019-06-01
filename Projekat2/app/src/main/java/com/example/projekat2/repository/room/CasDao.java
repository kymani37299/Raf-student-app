package com.example.projekat2.repository.room;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

@Dao
public interface CasDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<CasEntity> casovi);

    @Query("SELECT * FROM casovi")
    LiveData<List<CasEntity>> getAll();

    @Query("SELECT * FROM casovi WHERE " +
            "(:grupa=='' OR grupe LIKE '%' || :grupa || '%') AND " +
            "(:dan=='' OR dan==:dan) AND " +
            "(:textFilter=='' OR predmet LIKE :textFilter || '%' OR nastavnik LIKE '%' || :textFilter || '%') AND" +
            "(:ucionica=='' OR ucionica==:ucionica)")
    LiveData<List<CasEntity>> selectFiltered(String grupa, String dan, String textFilter,String ucionica);

    @Query("SELECT * FROM casovi WHERE id==:id")
    LiveData<CasEntity> getById(int id);
}
