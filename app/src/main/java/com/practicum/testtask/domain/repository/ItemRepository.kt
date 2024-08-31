package com.practicum.testtask.domain.repository

import com.practicum.testtask.data.model.Item
import kotlinx.coroutines.flow.Flow

interface ItemRepository {
    suspend fun getAllItems() : Flow<List<Item>>
    suspend fun insertItem(item : Item)
    suspend fun deleteItem(item: Item)
    suspend fun searchItems(query : String) : Flow<List<Item>>
    suspend fun updateItemAmount(amount : Int, id : Int)
}