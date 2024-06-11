package com.gkprojects.cmmsandroidapp.Models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.Users

class sharedUsersVM : ViewModel(){
    val user =MutableLiveData<Users>()

}