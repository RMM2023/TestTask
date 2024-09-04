package com.practicum.testtask.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "item")
data class Item(
    @PrimaryKey(autoGenerate = true) val id :Int,
    val name : String,
    val time : Long,
    val tags : List<String>,
    val amount : Int
)
