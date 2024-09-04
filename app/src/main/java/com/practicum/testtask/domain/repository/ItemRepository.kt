package com.practicum.testtask.domain.repository

import com.practicum.testtask.data.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    fun getAllItems() : Flow<List<Item>>
    fun insertItem(item : Item) : Long
    fun deleteItem(item: Item) : Int
    fun searchItems(query : String) : Flow<List<Item>>
    fun updateItemAmount(amount : Int, id : Int) : Int
}