package com.example.congregationinfo.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.moshi.Json


@Entity(tableName = "CongInfotable")
data class CongregationDataRoom(
        @PrimaryKey(autoGenerate = true) var CongInfoId : Long?,
        @ColumnInfo(name = "name") var name: String,
        @ColumnInfo(name = "firstStartCounter") var firstStartCounter: Int,
        @ColumnInfo(name = "hardCode") var counter: Int,
        @ColumnInfo(name = "updateDate") var updateDate: String,
        @ColumnInfo(name = "resultValues") var resultValues: List<List<String>>
)

data class ResultValues(val resultValues: String)


class ResultValuesTypeConverter {

        @TypeConverter
        fun fromString(value: String?): List<List<String>> {
                val listType =object : TypeToken<List<List<String>>>() {}.type
                return Gson().fromJson(value, listType)
        }
        @TypeConverter
        fun fromList(list: List<List<String>>): String {
                return Gson().toJson(list)
        }

}
