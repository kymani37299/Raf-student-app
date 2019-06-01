package com.example.projekat2.repository.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {CasEntity.class},version = 1)
public abstract class CasDB extends RoomDatabase {

    private static final String dbName = "casovi.db";

    private static volatile CasDB db;

    public abstract CasDao getCasDao();

    public static CasDB getDb(Context context) {
        if (db == null) {
            synchronized (CasDB.class){
                if (db == null) {
                    db = Room.databaseBuilder(
                            context.getApplicationContext(),
                            CasDB.class,
                            dbName)
                            .build();
                }
            }
        }

        return db;
    }
}
