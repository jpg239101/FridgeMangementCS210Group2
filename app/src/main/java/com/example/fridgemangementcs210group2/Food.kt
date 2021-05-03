package com.example.fridgemangementcs210group2

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
    val title: String = "",
    var isChecked: Boolean = false,
    var quantity: Int = 1
):Parcelable
