package com.example.congregationinfo.data

import androidx.room.*


@Dao
interface CongDAO {

    @Query("SELECT * FROM CongInfotable")
    fun getAllInfo(): List<CongregationDataRoom>

    @Insert
    fun insertInfo(vararg congregationDataRoom: CongregationDataRoom)

    @Delete
    fun deleteInfo(list:CongregationDataRoom)



}