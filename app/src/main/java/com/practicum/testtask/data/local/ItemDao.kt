package com.practicum.testtask.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.practicum.testtask.data.model.Item
import kotlinx.coroutines.flow.Flow

@Dao
interface ItemDao {
    @Query("SELECT * FROM item")
    fun getAllItems() : Flow<List<Item>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertItem(item : Item) : Long

    @Delete
    fun deleteItem(item: Item) : Int

    @Query("SELECT * FROM item WHERE name LIKE '%' || :query || '%'")
    fun searchItems(query : String) : Flow<List<Item>>

    @Query("UPDATE item SET amount = :amount WHERE id = :id")
    fun updateItemAmount(amount : Int, id : Int) : Int

}