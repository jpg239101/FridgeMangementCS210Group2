package com.example.fridgemangementcs210group2

import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class MainActivity : AppCompatActivity() {
    // creating a variable for our list view,
    // arraylist and firebase Firestore.
    var coursesLV: ListView? = null
    var dataModalArrayList: ArrayList<DataModal?>? = null
    var db: FirebaseFirestore? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // below line is use to initialize our variables
        coursesLV = findViewById(R.id.idLVCourses)
        dataModalArrayList = ArrayList()

        // initializing our variable for firebase
        // firestore and getting its instance.
        db = FirebaseFirestore.getInstance()

        // here we are calling a method
        // to load data in our list view.
        loadDatainListview()
    }

    private fun loadDatainListview() {
        // below line is use to get data from Firebase
        // firestore using collection in android.
        db!!.collection("Data").get()
            .addOnSuccessListener { queryDocumentSnapshots ->
                // after getting the data we are calling on success method
                // and inside this method we are checking if the received
                // query snapshot is empty or not.
                if (!queryDocumentSnapshots.isEmpty) {
                    // if the snapshot is not empty we are hiding
                    // our progress bar and adding our data in a list.
                    val list = queryDocumentSnapshots.documents
                    for (d in list) {
                        // after getting this list we are passing
                        // that list to our object class.
                        val dataModal = d.toObject(DataModal::class.java)

                        // after getting data from Firebase we are
                        // storing that data in our array list
                        dataModalArrayList!!.add(dataModal)
                    }
                    // after that we are passing our array list to our adapter class.
                    val adapter = CoursesLVAdapter(this@MainActivity, dataModalArrayList)

                    // after passing this array list to our adapter
                    // class we are setting our adapter to our list view.
                    coursesLV!!.adapter = adapter
                } else {
                    // if the snapshot is empty we are displaying a toast message.
                    Toast.makeText(
                        this@MainActivity,
                        "No data found in Database",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }
            }.addOnFailureListener { // we are displaying a toast message
                // when we get any error from Firebase.
                Toast.makeText(this@MainActivity, "Fail to load data..", Toast.LENGTH_SHORT).show()
            }
    }
}
