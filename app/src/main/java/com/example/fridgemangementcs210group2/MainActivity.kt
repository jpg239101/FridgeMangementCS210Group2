package com.example.fridgemangementcs210group2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
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
    private val fridgeCollectionRef = Firebase.firestore.collection("Fridges")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        fridgeAdapter = FridgeAdapter(mutableListOf())

        rvFridges.adapter = fridgeAdapter
        rvFridges.layoutManager = LinearLayoutManager(this)

        btnAddFridge.setOnClickListener{
            val fridgeName = etFridgeName.text.toString()
            if (fridgeName.isNotEmpty()){
                val fridge = Fridge(fridgeName,false, ArrayList<Food>(256))
                fridgeAdapter.addFridge(fridge)
                saveFridge(fridge)
                etFridgeName.text.clear()
                Toast.makeText(this,"Fridge Added",Toast.LENGTH_LONG).show()
            }
        }
        btnDeleteFridge.setOnClickListener{
            fridgeAdapter.deleteFridge()
            Toast.makeText(this,"Fridge Deleted",Toast.LENGTH_LONG).show()
        }

    }
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

}