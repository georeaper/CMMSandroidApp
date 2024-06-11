package com.gkprojects.cmmsandroidapp.Fragments.Settings

import android.content.Context
import androidx.lifecycle.LiveData
import com.gkprojects.cmmsandroidapp.CMMSDatabase
import com.gkprojects.cmmsandroidapp.Fragments.SpecialTools.RepoSpecialTools
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import com.gkprojects.cmmsandroidapp.DataClasses.Settings as AppSettings
import com.gkprojects.cmmsandroidapp.Dao.SettingsDao

class RepoSettings {

    companion object{
        var userDatabase: CMMSDatabase? = null

        private fun initialiseDB(context: Context): CMMSDatabase? {
            return CMMSDatabase.getInstance(context)!!
        }

        fun createStartingSettings(context: Context,settings :AppSettings){
            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("dd/MM/yy HH:mm:ss", Locale.getDefault())
            settings.DateCreated = dateFormat.format(currentDateTime)
            settings.LastModified=dateFormat.format(currentDateTime)
            userDatabase= initialiseDB(context)
            CoroutineScope(Dispatchers.IO).launch {
                userDatabase!!.SettingsDao().createSettings(settings)
            }
        }
        fun getAllSettings(context: Context):LiveData<List<AppSettings>>{
            userDatabase= initialiseDB(context)
            return userDatabase!!.SettingsDao().getAllSettings()
        }

    }
}