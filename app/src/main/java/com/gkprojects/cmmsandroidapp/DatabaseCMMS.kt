package com.gkprojects.cmmsandroidapp

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gkprojects.cmmsandroidapp.DataClasses.*
import com.gkprojects.cmmsandroidapp.Repository.*

@Database(
    entities = [Hospital::class,
        Equipment::class,
       Contract::class,
        ContractEquipment::class,
        Maintenance::class, SparePart::class,
        Cases::class
       ],
    version =1,
    exportSchema = true,
    //autoMigrations = [AutoMigration (from = 1, to = 2) ]
)
abstract class CMMSDatabase : RoomDatabase() {

    abstract fun hospitalDAO(): HospitalDAO
    abstract fun equipmentDAO(): EquipmentDAO
    abstract fun contractDAO(): ContractDAO
    abstract fun contractEquipmentDAO(): ContractEquipmentDAO
    abstract fun maintenanceDAO(): MaintenanceDAO
    abstract fun sparePartDAO(): SparePartDAO
    abstract fun casesDAO():CasesDao


   //companion object CMMSDatabaseProvider {
       companion object  {
       @Volatile
       private var instance: CMMSDatabase? = null
       private const val DATABASE_NAME="cmms_database2"
       fun getInstance(context: Context):CMMSDatabase?
       {
           if(instance == null)
           {
               synchronized(CMMSDatabase::class.java)
               {
                   if(instance == null)
                   {
                       instance= Room.databaseBuilder(context,CMMSDatabase::class.java,
                           DATABASE_NAME)
                           .fallbackToDestructiveMigration()
                           .build()
                   }
               }
           }

           return instance
       }


    }

}

