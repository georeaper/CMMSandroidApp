package com.gkprojects.cmmsandroidapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CustomerData(@PrimaryKey(autoGenerate = true)  val id: Int?,
@ColumnInfo(name = "name") val name: String?,
@ColumnInfo(name = "address") val address: String?,
@ColumnInfo(name = "email") val email: String?,
@ColumnInfo(name = "date") val date: String?)
