package com.example.byteduo.UI

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager

import android.widget.EditText
import android.widget.ListView
import com.example.byteduo.Bakery
import com.example.byteduo.Drinks
import com.example.byteduo.HotCoffee
import com.example.byteduo.HotTeas
import com.example.byteduo.IceTeas
import com.example.byteduo.R
import com.example.byteduo.adapter.MenuAdapter
import com.example.byteduo.model.MenuItem

class Menu : AppCompatActivity() {

    private lateinit var menuListView: ListView
    private lateinit var menuAdapter: MenuAdapter

    private val menuItems = listOf("Hot Coffee","Ice Teas","Hot Teas", "Bakery", "Drinks")
    private val fragments = listOf(
        HotCoffee(),
        IceTeas(),
        HotTeas(),
        Bakery(),
        Drinks())

    //creating initial menu items
    private val menuItemsList = mutableListOf<MenuItem>()


    private val initialMenuItems = listOf(
        MenuItem(
            itemId = 1,
            itemName = "Black Coffee",
            itemPicture = "url_to_black_coffee_image",
            itemIngredients = listOf("Coffee"),
            itemPrice = 2.99,
            category = "Hot Coffee"
        ),
        MenuItem(
            itemId = 2,
            itemName = "Latte",
            itemPicture = "url_to_latte_image",
            itemIngredients = listOf("Espresso", "Steamed Milk"),
            itemPrice = 3.99,
            category = "Hot Coffee"
        )
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)


        //get the list input
        menuListView = findViewById(R.id.menuListView)

        // Get menu items from a resource (replace with your own list)
        //val menuItems = resources.getStringArray(R.array.menu_items)

        // Create adapter and set it to the ListView
        menuAdapter = MenuAdapter(this, menuItems)
        menuListView.adapter = menuAdapter

        // Set the initial selected position
        val initialSelectedPosition = 2
        menuAdapter.setSelectedPosition(initialSelectedPosition)

        // Set up item click listener to handle item selection
        menuListView.setOnItemClickListener { _, _, position, _ ->

            onMenuItemClicked(position)
        }

        // Display the default fragment (e.g., Hot Teas) when the activity starts
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragments[2])
                .commit()


        //get the search input
        val editText = findViewById<EditText>(R.id.txtSearch)


        //the search bar
        // Set an OnClickListener to enable focus when clicked
        editText.setOnClickListener {
            editText.isFocusableInTouchMode = true
            editText.requestFocus()
            //on click the keyboard will pop up
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }

    }
    private fun onMenuItemClicked(position: Int) {
        // Replace the fragment container with the selected fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragments[position])
            .commit()

        menuAdapter.setSelectedPosition(position)
    }
}