package com.gkprojects.cmmsandroidapp.Repository
import androidx.lifecycle.MutableLiveData
import com.gkprojects.cmmsandroidapp.DataClasses.Customers
import com.google.firebase.database.*

class CustomersRepository {

    private val databaseReference: DatabaseReference=FirebaseDatabase.getInstance().getReference("customers")
@Volatile private var INSTANCE :CustomersRepository?=null

    fun getInstance() :CustomersRepository{
        return INSTANCE ?: synchronized(this){
            val instance=CustomersRepository()
            INSTANCE=instance
            instance
        }

    }
    fun loadCustomers(customerlist :MutableLiveData<List<Customers>>){
        databaseReference.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                try {
                    val _customerList:List<Customers> =snapshot.children.map { dataSnapshot->
                        dataSnapshot.getValue(Customers::class.java)!!

                    }
                    customerlist.postValue(_customerList)

                }catch (e:java.lang.Exception){

                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })

    }

}