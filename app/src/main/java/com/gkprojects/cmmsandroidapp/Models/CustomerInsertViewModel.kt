package com.gkprojects.cmmsandroidapp.Models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.Repository.CustomersRepository
import com.gkprojects.cmmsandroidapp.Repository.InsertCustomerRepo

class CustomerInsertViewModel:ViewModel() {

    private val repository : InsertCustomerRepo



    init {
        repository= InsertCustomerRepo().getInstance()


    }
}