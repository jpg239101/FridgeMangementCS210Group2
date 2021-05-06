package com.example.fridgemangementcs210group2

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fridge_item.*
import kotlinx.android.synthetic.main.fridge_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

class FridgeAdapter(
    private val fridges: MutableList<Fridge>
) : RecyclerView.Adapter<FridgeAdapter.FridgeViewHolder>() {
    class FridgeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    //links recycler view objects to the fridge_item.xml
    private val fridgeCollectionRef = Firebase.firestore.collection("Fridges")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FridgeViewHolder {
        return FridgeViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.fridge_item,
                parent,
                false

            )
        )
    }

    //adds a fridge to the list
    fun addFridge(fridge: Fridge) {
        fridges.add(fridge)
        notifyItemInserted(fridges.size - 1)
    }

    //deletes a fridge from the list
    fun deleteFridge() {
        fridges.removeAll { fridge ->
            fridge.isChecked
        }
        notifyDataSetChanged()
    }

    //sets up actions on the fridge item including setting the title and link to the item list
    override fun onBindViewHolder(holder: FridgeViewHolder, position: Int) {
        val curFridge = fridges[position]
        val context = holder.itemView.context
        holder.itemView.apply {
            tvFridgeTitle.text = curFridge.title
            cbDone.isChecked = curFridge.isChecked
            cbDone.setOnCheckedChangeListener { _, isChecked ->
                updateFridge(curFridge.title)
                curFridge.isChecked = !curFridge.isChecked
            }
            btnSeeItems.setOnClickListener {
                Intent(context, InventoryActivity::class.java).also {
                    //it.putExtra("EXTRA_FRIDGE",curFridge.title)
                    context.startActivity(it)
                }

            }
        }
    }

    private fun updateFridge(title: String) = CoroutineScope(Dispatchers.IO).launch {
        val querySnapshot = fridgeCollectionRef
            .whereEqualTo("title", title)
            .get()
            .await()
        if (querySnapshot.documents.isNotEmpty()) {
            for (document in querySnapshot) {
                try {
                    fridgeCollectionRef.document(document.id).update("checked", false).await()
                } catch (e: Exception) {
                    Log.e("Update Food List", "Failure")
                }
            }
        } else {
            Log.e("update list", "Failure could not find fridge")
        }
    }

    //number of fridges
    override fun getItemCount(): Int {
        return fridges.size
    }
}

