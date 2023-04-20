package com.gkprojects.cmmsandroidapp

import androidx.room.Dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy


@Dao
interface CustomerDao {
//    @Query("SELECT * FROM CustomerData")
//    suspend fun getAll() :List<CustomerData>

    @Insert(onConflict=OnConflictStrategy.IGNORE)
    suspend fun insertAll(vararg customer: CustomerData)
//
//    @Delete
//    suspend fun delete(customer: CustomerData)
}