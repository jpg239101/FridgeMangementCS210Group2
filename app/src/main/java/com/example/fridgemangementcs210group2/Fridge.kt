package com.example.fridgemangementcs210group2

data class Fridge (
    val title: String = "",
    var isChecked: Boolean = false,
    var items: ArrayList<Food> = ArrayList<Food>(256)
)