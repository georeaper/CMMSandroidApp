package com.gkprojects.cmmsandroidapp

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gkprojects.cmmsandroidapp.DataClasseUNused.MaintenanceFieldForm
import com.gkprojects.cmmsandroidapp.DataClasseUNused.MaintenanceInventory
import com.gkprojects.cmmsandroidapp.DataClasses.*
import com.gkprojects.cmmsandroidapp.Fragments.Inventory.InventoryDao
import com.gkprojects.cmmsandroidapp.Fragments.SpecialTools.ToolsDao
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.FieldReportInventoryDao
import com.gkprojects.cmmsandroidapp.Fragments.WorkOrders.FieldReportToolsDao
import com.gkprojects.cmmsandroidapp.Repository.*

@Database(
    entities = [Customer::class,
        Inventory::class,
        Contracts::class,
        ContractEquipments::class,
        Departments::class,
        Equipments::class,
        FieldReportEquipment::class,
        FieldReportInventory::class,
        FieldReports::class,
        Maintenances::class,
        Manufacturer::class,
        ModelAsset::class,
        CategoryAsset::class,
 //       MaintenanceFieldForm::class,
 //       MaintenanceInventory::class,
        Tickets::class,
        Users::class,
        CheckForms::class,
        Settings::class,
        Tools::class,
        FieldReportCheckForm::class,
        FieldReportTools::class
               ],
    version =1,
    exportSchema = true
  //  ,autoMigrations = [AutoMigration (from = 1, to = 2)
 //       , AutoMigration (from = 2, to = 3),
//        ,AutoMigration (from = 3, to = 4)
//        ,AutoMigration (from = 4, to = 5)
     //  ]
)
abstract class CMMSDatabase : RoomDatabase() {

    abstract fun CustomerDao(): CustomerDao
    abstract fun EquipmentsDAO(): EquipmentsDao
    abstract fun DepartmentsDao(): DepartmentsDao
    abstract fun ContractEquipmentsDao(): ContractEquipmentsDao
    abstract fun ContractsDao(): ContractsDao
    abstract fun FieldReportEquipmentDao(): FieldReportEquipmentDao
    abstract fun FieldReportsDao():FieldReportsDao
    //abstract fun InventoryDao():InventoryDao
    abstract fun InventoryDao():InventoryDao
//    abstract fun MaintenanceFieldFormDao():MaintenanceFieldFormDao
//    abstract fun MaintenanceInventoryDao():MaintenanceInventoryDao
    abstract fun MaintenancesDao():MaintenancesDao
    abstract fun TicketsDao():TicketsDao
    abstract fun UsersDao():UsersDao
    abstract fun FieldReportInventoryDao():FieldReportInventoryDao

    abstract fun FieldReportToolsDao(): FieldReportToolsDao
    abstract fun ToolsDao(): ToolsDao
    abstract fun CheckFormsDao():CheckFormsDao
    abstract fun FieldReportCheckFormsDao():FieldReportCheckFormsDao



       companion object  {
       @Volatile
       private var instance: CMMSDatabase? = null
       //private const val DATABASE_NAME="CmmsDb"
       //private const val DATABASE_NAME="CmmsDb5"
       //private const val DATABASE_NAME="cmmsAppDB29012024"
       //private const val DATABASE_NAME="cmmsAppDB11022024b"
       private const val DATABASE_NAME="cmmsAppDB19032024_b" //ID because UUID Strings
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

