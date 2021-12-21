package com.example.congregationinfo.data

import androidx.room.*


@Dao
interface CongDAO {

    @Query("SELECT * FROM conginfotable")
    fun getAllInfo(): List<CongregationDataRoom>

    @Insert
    fun insertInfo(vararg congregationDataRoom: CongregationDataRoom)

    @Query("DELETE FROM conginfotable")
    fun deleteAll()

}

