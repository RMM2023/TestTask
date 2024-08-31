package com.practicum.testtask.data.local

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun fromString(string : String) : List<String> = string.split(",")

    @TypeConverter
    fun toString(list : List<String>) : String = list.joinToString ( "," )
}