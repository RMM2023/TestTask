package com.practicum.testtask.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.practicum.testtask.data.model.Item

@Database(entities = [Item::class], version = 1)
@TypeConverters(Converters :: class)
abstract class AppDataBase : RoomDatabase(){
    abstract fun itemDao() : ItemDao
    companion object{
        private var instance : AppDataBase? = null
        fun getInstance(context: Context) : AppDataBase{
            return instance ?: synchronized(this){
                instance ?: buildDataBase(context).also { instance = it }
            }
        }
        private fun buildDataBase(context: Context) : AppDataBase{
            return Room.databaseBuilder(context, AppDataBase::class.java, "productDataBase")
                .createFromAsset("data.db")
                .build()
        }
    }

}