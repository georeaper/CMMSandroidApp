package com.gkprojects.cmmsandroidapp.Repository

import com.google.firebase.database.*

class InsertCustomerRepo {
    private val table :String ="count"
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference(table)
    @Volatile private var INSTANCE :InsertCustomerRepo?=null


    fun getInstance() :InsertCustomerRepo{
        return INSTANCE ?: synchronized(this){
            val instance=InsertCustomerRepo()
            INSTANCE=instance
            instance
        }

    }





    }