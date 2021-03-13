package com.picpay.desafio.android.view.users

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.picpay.desafio.android.R
import com.picpay.desafio.android.view.users.adapters.UserListAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var adapter: UserListAdapter
    private val viewModel: UsersViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.user_list_progress_bar)

        adapter = UserListAdapter()
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        progressBar.visibility = View.VISIBLE

        configureObservers()
    }

    private fun configureObservers() {
        viewModel.contacts.observe(this, Observer { users ->
            progressBar.visibility = View.GONE
            adapter.users = users
        })

        viewModel.error.observe(this, Observer {
            val message = getString(R.string.error)

            progressBar.visibility = View.GONE
            recyclerView.visibility = View.GONE

            Toast.makeText(this@UsersActivity, message, Toast.LENGTH_SHORT).show()
        })
    }

}
