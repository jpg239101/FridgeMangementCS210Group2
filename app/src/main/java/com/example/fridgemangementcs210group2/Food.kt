package com.example.fridgemangementcs210group2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

//Food data class that sets up food objects
@Parcelize
data class Food(
    val title: String = "",
    var isChecked: Boolean = false,
    var quantity: Int = 1,
    val bestByDate: String = "1-01-2099"
) : Parcelable
