package com.example.byteduo.model

class Categories {
    private val menuItems: MutableList<MenuItem> = mutableListOf()

    // Add a new item to the menu
    fun addItem(item: MenuItem) {
        menuItems.add(item)
    }

    // Remove an item from the menu
    fun removeItem(itemId: Long) {
        val itemToRemove = menuItems.find { it.itemId == itemId }
        itemToRemove?.let {
            menuItems.remove(it)
        }
    }

    // Get the entire menu
    fun getMenu(): List<MenuItem> {
        return menuItems.toList()
    }
}