package com.example.fridgemangementcs210group2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fridge_item.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private lateinit var fridgeAdapter: FridgeAdapter
    private var dbFridgeList = mutableListOf<Fridge>()
    private val fridgeCollectionRef = Firebase.firestore.collection("Fridges")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //gets list of fridges from firebase
        retrieveFridge()
        //creates a fridge adapter for the recycler view
        fridgeAdapter = FridgeAdapter(dbFridgeList)
        //sets up recycler view

        //subscribe to realtime updates
        //subscribeToRealtimeUpdates()
        rvFridges.adapter = fridgeAdapter
        rvFridges.layoutManager = LinearLayoutManager(this)
        //button to add a fridge to the list
        btnAddFridge.setOnClickListener{
            val fridgeName = etFridgeName.text.toString()
            if (fridgeName.isNotEmpty()){
                val fridge = Fridge(fridgeName,false, ArrayList<Food>(256))
                fridgeAdapter.addFridge(fridge)
                //saves fridge to firestore
                saveFridge(fridge)
                etFridgeName.text.clear()
                Toast.makeText(this,"Fridge Added",Toast.LENGTH_LONG).show()
            }
        }
        //button to delete fridges with checked boxes
        btnDeleteFridge.setOnClickListener{
            fridgeAdapter.deleteFridge()
            Toast.makeText(this,"Fridge Deleted",Toast.LENGTH_LONG).show()
        }

    }
    private fun deleteFridge() = CoroutineScope(Dispatchers.IO).launch{
        val querySnapshot = fridgeCollectionRef
            .get()
            .await()
        if(querySnapshot.documents.isNotEmpty()) {
            for(document in querySnapshot) {
                try {
                    val fridge = document.toObject<Fridge>()
                    if(fridge.isChecked){
                        fridgeCollectionRef.document(document.id).delete()
                            .addOnSuccessListener { Log.d("main act","DocumentSnapshot successfully deleted!") }
                            .addOnFailureListener { e -> Log.w("main act", "Error deleting document", e) }
                    }
                    } catch (e: Exception) {
                    Log.e("Update Food List","Failure")
                }
            }
        } else {
            Log.e("update list","Failure could not find fridge")
        }
    }

    //sets up the saving of a fridge objet to the firestore
    private fun saveFridge(fridge: Fridge) = CoroutineScope(Dispatchers.IO).launch {
        try {
            fridgeCollectionRef.add(fridge).await()
            withContext(Dispatchers.Main){
                Toast.makeText(this@MainActivity,"Successfully saved", Toast.LENGTH_LONG).show()
            }
        }catch (e: Exception){
            withContext(Dispatchers.Main){
                Toast.makeText(this@MainActivity,e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
    private fun subscribeToRealtimeUpdates(){
        fridgeCollectionRef.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            firebaseFirestoreException?.let {
                Toast.makeText(this,it.message,Toast.LENGTH_LONG).show()
                return@addSnapshotListener
            }
            querySnapshot?.let {
                for (document in querySnapshot.documents){
                    val fridge = document.toObject<Fridge>()
                    if (fridge != null) {
                        if (!dbFridgeList.contains(fridge)) {
                            dbFridgeList.add(fridge)
                            fridgeAdapter.addFridge(fridge)
                        }
                    }
                }
            }
        }
    }
    private fun retrieveFridge() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = fridgeCollectionRef.get().await()
            for(document in querySnapshot.documents) {
                val fridge = document.toObject<Fridge>()
                if (fridge != null) {
                    dbFridgeList.add(fridge)
                }
            }
        } catch(e: Exception) {
            Log.e("Retrieve fridge list","Failure")
        }

    }

}