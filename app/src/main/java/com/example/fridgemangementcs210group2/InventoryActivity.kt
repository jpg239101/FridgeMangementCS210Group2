package com.example.fridgemangementcs210group2

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
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

private const val REQUEST_CODE = 42

class InventoryActivity : AppCompatActivity() {
    // promises to create a food adapter
    private lateinit var foodAdapter: FoodAdapter

    //private val fridgeCollectionRef = Firebase.firestore.collection("Fridges")

    //private val fridgeTitle =  intent.getStringExtra("EXTRA_FRIDGE")
    //array containing food objects for the recycler view list
    private var foodList: ArrayList<Food> = ArrayList<Food>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inventory)
        //retrieveFoodList()
        //instantiate food object
        foodAdapter = FoodAdapter(foodList)

        //set up recycler view
        rvFood.adapter = foodAdapter
        rvFood.layoutManager = LinearLayoutManager(this)

        //add a food to the recycler view when the add button is clicked
        btnAddFood.setOnClickListener {
            val foodName = etFoodName.text.toString()
            if (foodName.isNotEmpty()) {
                val food = Food(foodName, false, 1)
                foodAdapter.addFood(food)

                Toast.makeText(this, "Successfully added", Toast.LENGTH_LONG).show()

                etFoodName.text.clear()
            }
        }
        //delete food
        btnDeleteFood.setOnClickListener {
            foodAdapter.deleteFood()
            Toast.makeText(this, "Successfully deleted", Toast.LENGTH_LONG).show()

        }
        //link to new activity counting a barcode scanner for upcs
        btnScanLabel.setOnClickListener {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            if (takePictureIntent.resolveActivity(this.packageManager) != null) {
                startActivityForResult(takePictureIntent, REQUEST_CODE)
            } else {
                Toast.makeText(this, "unable to open camera", Toast.LENGTH_LONG).show()
            }

        }
    }
}
//tried to add firebase integration for storage of the food arrays couldn't figure my way around a null pointer
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