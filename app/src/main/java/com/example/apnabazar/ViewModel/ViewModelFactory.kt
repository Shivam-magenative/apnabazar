package com.example.apnabazar.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.apnabazar.backend.Repositories

class ViewModelFactory(private val repository: Repositories) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CollectionVIewModel::class.java)) {
            return CollectionVIewModel(repository) as T
        }
        if (modelClass.isAssignableFrom(ProductViewModel::class.java)){
            return ProductViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }
}