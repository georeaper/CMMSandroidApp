package com.gkprojects.cmmsandroidapp.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.Customers
import com.gkprojects.cmmsandroidapp.Repository.CustomersRepository

class CustomersViewModel :ViewModel() {
    private val repository :CustomersRepository
    private val _allCustomers = MutableLiveData<List<Customers>>()
    val allCustomers :LiveData<List<Customers>> = _allCustomers

    init {
        repository=CustomersRepository().getInstance()
        repository.loadCustomers(_allCustomers)

    }

}