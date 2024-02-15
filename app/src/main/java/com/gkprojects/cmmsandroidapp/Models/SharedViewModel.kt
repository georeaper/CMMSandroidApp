package com.gkprojects.cmmsandroidapp.Models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel: ViewModel() {
    val reportId = MutableLiveData<Int>()
}