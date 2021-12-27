package com.example.congregationinfo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "CongInfotable")
data class CongregationDataRoom(
        @PrimaryKey(autoGenerate = true) var CongInfoId : Long?,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "firstStartCounter") var firstStartCounter: Int,
        @ColumnInfo(name = "hardCode") var hardCode: String,
)
