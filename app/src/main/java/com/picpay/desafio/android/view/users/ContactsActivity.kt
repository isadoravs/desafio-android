package com.picpay.desafio.android.view.users

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.view.users.adapters.UserListAdapter

class ContactsActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter
    private lateinit var viewModel: ContactsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(ContactsViewModel::class.java)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)

        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        progressBar.visibility = View.VISIBLE

        viewModel.fetchContacts()

        configureObservers()
    }

    private fun configureObservers() {

        viewModel.contacts.observe(this, Observer {contacts ->
            progressBar.visibility = View.GONE
            adapter.users = contacts
        })

        viewModel.error.observe(this, Observer {_ ->
            val message = getString(R.string.error)

            progressBar.visibility = View.GONE
            recyclerView.visibility = View.GONE

            Toast.makeText(this@ContactsActivity, message, Toast.LENGTH_SHORT)
                .show()
        })
    }

}
