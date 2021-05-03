package com.example.fridgemangementcs210group2

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.activity_inventory.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await

class InventoryActivity: AppCompatActivity() {

    private lateinit var foodAdapter: FoodAdapter
    //private val fridgeCollectionRef = Firebase.firestore.collection("Fridges")

    //private val fridgeTitle =  intent.getStringExtra("EXTRA_FRIDGE")
    private var foodList: ArrayList<Food> = ArrayList<Food>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)
        //retrieveFoodList()
        foodAdapter = FoodAdapter(foodList)

        rvFood.adapter = foodAdapter
        rvFood.layoutManager = LinearLayoutManager(this)

        btnAddFood.setOnClickListener {
            val foodName = etFoodName.text.toString()
            if (foodName.isNotEmpty()) {
                val food = Food(foodName, false, 1)
                foodAdapter.addFood(food)
                etFoodName.text.clear()
            }
        }
        btnDeleteFood.setOnClickListener {
            foodAdapter.deleteFood()
        }
        btnScanLabel.setOnClickListener {
            // to be implemented
        }
    }
}

   /* private fun updateFridge() = CoroutineScope(Dispatchers.IO).launch {
        val querySnapshot = fridgeCollectionRef
            .whereEqualTo("title",fridgeTitle)
            .get()
            .await()
        if(querySnapshot.documents.isNotEmpty()) {
            for(document in querySnapshot) {
                try {
                    fridgeCollectionRef.document(document.id).update("items",foodAdapter.foods ).await()
                } catch (e: Exception) {
                    Log.e("Update Food List","Failure")
                }
            }
        } else {
            Log.e("update list","Failure could not find fridge")
        }
    }

    private fun retrieveFoodList() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val querySnapshot = fridgeCollectionRef
                .whereEqualTo("title",fridgeTitle)
                .get()
                .await()
            for(document in querySnapshot.documents) {
                val fridge = document.toObject<Fridge>()
                if (fridge != null) {
                    foodList = fridge.items
                }
            }
        } catch(e: Exception) {
            Log.e("Retrieve Food List","Failure")
        }

    }
}*/