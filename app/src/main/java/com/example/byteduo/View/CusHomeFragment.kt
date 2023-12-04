package com.example.byteduo.View

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import com.example.byteduo.R
import com.example.byteduo.adapter.MenuAdapter
import com.example.byteduo.model.Customer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CusHomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CusHomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var menuListView: ListView
    private lateinit var menuAdapter: MenuAdapter

    private val menuItems = listOf("Hot Coffee","Ice Teas","Hot Teas", "Bakery", "Drinks")
    private val fragments = listOf(
        HotCoffee(),
        IceTeas(),
        HotTeas(),
        Bakery(),
        Drinks()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cus_home, container, false)

        // Initialization code
        val userName = view.findViewById<TextView>(R.id.txtUserName)
        menuListView = view.findViewById(R.id.menuListView)
        menuAdapter = MenuAdapter(requireActivity(), menuItems)
        menuListView.adapter = menuAdapter

        val editText = view.findViewById<EditText>(R.id.txtSearch)

        // Set an OnClickListener to enable focus when clicked
        editText.setOnClickListener {
            editText.isFocusableInTouchMode = true
            editText.requestFocus()
            val imm =
                requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        }

        val userId = FirebaseAuth.getInstance().currentUser?.uid

        if (userId != null) {
            val database = FirebaseDatabase.getInstance().reference
            val customerReference = database.child("customers").child(userId)

            customerReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        val customer = snapshot.getValue(Customer::class.java)
                        if (customer != null) {
                            requireActivity().runOnUiThread {
                                val name = customer.cusUsername ?: "Unknown User"
                                userName.text = name
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle the error
                }
            })
        }

        // Item click listener
        menuListView.setOnItemClickListener { _, _, position, _ ->
            onMenuItemClicked(position)
        }

        return view
    }

    private fun onMenuItemClicked(position: Int) {
        // Replace the fragment container with the selected fragment
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragments[position])
            .commit()

        menuAdapter.setSelectedPosition(position)
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CusHomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CusHomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}