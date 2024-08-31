package com.practicum.testtask.data.repository

import com.practicum.testtask.data.local.ItemDao
import com.practicum.testtask.data.model.Item
import com.practicum.testtask.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class ItemRepositoryImpl(private val itemDao : ItemDao) : ItemRepository {
    override suspend fun getAllItems(): Flow<List<Item>> {
        return itemDao.getAllItems()
    }

    override suspend fun insertItem(item: Item) {
        return itemDao.insertItem(item)
    }

    override suspend fun deleteItem(item: Item) {
        return itemDao.deleteItem(item)
    }

    override suspend fun searchItems(query: String): Flow<List<Item>> {
        return itemDao.searchItems(query)
    }

    override suspend fun updateItemAmount(amount: Int, id: Int) {
        return itemDao.updateItemAmount(amount, id)
    }
}