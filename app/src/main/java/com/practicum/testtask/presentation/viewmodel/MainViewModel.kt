package com.practicum.testtask.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.testtask.data.model.Item
import com.practicum.testtask.domain.repository.ItemRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class MainViewModel(private val repository: ItemRepository) : ViewModel() {
    val _items = MutableStateFlow<List<Item>>(emptyList())
    val items = _items.asStateFlow()
    init {
        viewModelScope.launch {
            repository.getAllItems().collect{
                _items.value = it
            }
        }
    }
    fun searchItems(query : String){
        viewModelScope.launch {
            repository.searchItems(query).collect{
                _items.value = it
            }
        }
    }
    fun updateItemAmount(id : Int, amount : Int){
        viewModelScope.launch(Dispatchers.IO) {
            val updatedRows = repository.updateItemAmount(amount, id)
        }
    }
    fun deleteItem(item: Item){
        viewModelScope.launch(Dispatchers.IO) {
            val deleteRows = repository.deleteItem(item)
        }
    }
}