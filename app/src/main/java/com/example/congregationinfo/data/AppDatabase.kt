package com.example.congregationinfo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(CongregationData::class), version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun congDao():CongDAO

    companion object{
        private var INSTANCE: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            if(INSTANCE==null){
                INSTANCE= Room.databaseBuilder(context.getApplicationContext(),AppDatabase::class.java,"conginfo.db").build()
            }
            return INSTANCE!!
        }
        fun destroyInstance(){
            INSTANCE = null
        }
    }
}