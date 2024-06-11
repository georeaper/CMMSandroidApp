package com.gkprojects.cmmsandroidapp.Dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.gkprojects.cmmsandroidapp.DataClasses.Settings as AppSettings

@Dao
interface SettingsDao {
    @Insert
    fun createSettings(settings : AppSettings)

    @Query("Select * from Settings")
    fun getAllSettings(): LiveData<List<AppSettings>>
}