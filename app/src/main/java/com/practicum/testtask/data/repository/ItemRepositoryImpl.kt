package com.practicum.testtask.data.repository

import com.practicum.testtask.data.local.ItemDao
import com.practicum.testtask.data.model.Item
import com.practicum.testtask.domain.repository.ItemRepository
import kotlinx.coroutines.flow.Flow

class ItemRepositoryImpl(private val itemDao : ItemDao) : ItemRepository {
    override fun getAllItems(): Flow<List<Item>> {
        return itemDao.getAllItems()
    }

    override fun insertItem(item: Item): Long {
        return itemDao.insertItem(item)
    }

    override fun deleteItem(item: Item): Int {
        return itemDao.deleteItem(item)
    }

    override fun searchItems(query: String): Flow<List<Item>> {
        return itemDao.searchItems(query)
    }

    override fun updateItemAmount(amount: Int, id: Int): Int {
        return itemDao.updateItemAmount(amount, id)
    }
}