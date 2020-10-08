package com.example.roomapp.fragment.update

import android.app.AlertDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs

import com.example.roomapp.R
import com.example.roomapp.fragment.viewmodel.UserViewModel
import com.example.roomapp.model.User
import kotlinx.android.synthetic.main.fragment_update.*
import kotlinx.android.synthetic.main.fragment_update.view.*
import kotlinx.android.synthetic.main.fragment_update.view.editText_fragment_update_first_name


class UpdateUserFragment : Fragment() {

    private val args by navArgs<UpdateFragmentArgs>()
    private lateinit var  mUserViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_update, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        view.editText_fragment_update_first_name.setText(args.currentUser.firstName)
        view.editText_fragment_update_last_name.setText(args.currentUser.lastName)
        view.editText_fragment_update_age.setText(args.currentUser.age.toString())

        view.button_update.setOnClickListener {
            updateItem()
        }

        setHasOptionsMenu(true)

        return view
    }

    private fun updateItem() {
        val firstName =editText_fragment_update_first_name.text.toString()
        val lastName = editText_fragment_update_last_name.text.toString()
        val age = Integer.parseInt(editText_fragment_update_age.text.toString())

        if (inputCheck(firstName,lastName,editText_fragment_update_age.text))
        {
            val updateUser = User(args.currentUser.id, firstName, lastName, age)
            mUserViewModel.updateUser(updateUser)
            Toast.makeText(requireContext(),"Update Success ", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        else
        {
            Toast.makeText(requireContext(),"Input update null ",Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete)
        {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser() {
            val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(requireContext(),"Delete Success ", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle(" Delete ${args.currentUser.firstName} ?")
        builder.setMessage("Are you sure about that?")
        builder.create().show()

    }

}
