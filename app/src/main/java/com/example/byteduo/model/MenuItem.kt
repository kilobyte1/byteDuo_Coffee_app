package com.example.byteduo.model

data class MenuItem(
    val itemId: Long,
    val itemName: String,
    val itemPicture: String,   // Assuming itemPicture is a URL or a path to the image
    val itemIngredients: List<String>,
    val itemPrice: Double,
    val category: String){


}