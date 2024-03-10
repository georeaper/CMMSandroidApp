package com.gkprojects.cmmsandroidapp

import android.util.Log
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.gkprojects.cmmsandroidapp.DataClasses.Customer
import com.gkprojects.cmmsandroidapp.DataClasses.Equipments
import com.gkprojects.cmmsandroidapp.Repository.CustomerDao
import com.gkprojects.cmmsandroidapp.Repository.EquipmentsDao
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class FgnKeyTest {
    private lateinit var db: CMMSDatabase
    private lateinit var customerDao: CustomerDao
    private lateinit var equipmentDao: EquipmentsDao

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, CMMSDatabase::class.java).build()
        customerDao = db.CustomerDao()
        equipmentDao = db.EquipmentsDAO()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun testForeignKeyConstraint() {
        val customer = Customer(CustomerID = 1,null,null,null,null,null,null,null,null,null,null,null,null,null)
        customerDao.addCustomer(customer)
        //customerDao.insertCustomer(customer)

        val equipment = Equipments(EquipmentID = 1,
            CustomerID = 1,
            RemoteID = null,
            Notes = null,
            Warranty = null,
            Description = null,
            EquipmentCategory = null,
            EquipmentStatus =  null ,
            SerialNumber=null,
            EquipmentVersion = null,
            InstallationDate = null,
            LastModified = null,
            Model = null,
            Name = null,
            Manufacturer = null,
            Version = null,
            DateCreated =null )

        equipmentDao.addEquipments(equipment)

        // Update CustomerID in Customer
        customer.CustomerID = 2
        customerDao.updateCustomer(customer)

        // Check if CustomerID in Equipment has been updated
        //val updatedEquipment = equipmentDao.getEquipment(1)
        val updatedEquipment = equipmentDao.getAllDataEquipmentsByCustomerID(2)

        Log.d("TestCascadeOnUpdate","$updatedEquipment")
        //assertEquals(2, updatedEquipment)
    }
}