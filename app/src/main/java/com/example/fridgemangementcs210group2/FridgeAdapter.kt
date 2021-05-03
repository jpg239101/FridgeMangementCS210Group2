package com.example.fridgemangementcs210group2

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fridge_item.*
import kotlinx.android.synthetic.main.fridge_item.view.*

class FridgeAdapter(
    private val fridges: MutableList<Fridge>
): RecyclerView.Adapter<FridgeAdapter.FridgeViewHolder>() {
    class FridgeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    //links recycler view objects to the fridge_item.xml
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
    fun addFridge(fridge: Fridge){
        fridges.add(fridge)
        notifyItemInserted(fridges.size-1)
    }
    //deletes a fridge from the list
    fun deleteFridge(){
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
            cbDone.setOnCheckedChangeListener{_,isChecked ->
                curFridge.isChecked = !curFridge.isChecked
            }
            btnSeeItems.setOnClickListener {
                Intent(context, InventoryActivity::class.java).also{
                    //it.putExtra("EXTRA_FRIDGE",curFridge.title)
                    context.startActivity(it)
                }

            }
        }
    }
    //number of fridges
    override fun getItemCount(): Int {
        return fridges.size
    }
}

