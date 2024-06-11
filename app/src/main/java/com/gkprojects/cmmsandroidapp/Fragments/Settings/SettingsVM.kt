package com.gkprojects.cmmsandroidapp.Fragments.Settings

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gkprojects.cmmsandroidapp.DataClasses.Settings as AppSettings

class SettingsVM : ViewModel() {
    fun insertSettings(context: Context,settings : AppSettings){
        RepoSettings.createStartingSettings(context, settings)
    }
    fun getAllSettings(context: Context):LiveData<List<AppSettings>>{
        return RepoSettings.getAllSettings(context)
    }
}