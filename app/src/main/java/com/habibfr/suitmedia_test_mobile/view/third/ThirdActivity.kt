package com.habibfr.suitmedia_test_mobile.view.third

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.habibfr.githubusersapp.ui.UserAdapter
import com.habibfr.suitmedia_test_mobile.data.remote.Result
import com.habibfr.suitmedia_test_mobile.data.remote.api.response.DataUser
import com.habibfr.suitmedia_test_mobile.databinding.ActivityThirdBinding
import com.habibfr.suitmedia_test_mobile.view.factory.ViewModelFactory
import com.habibfr.suitmedia_test_mobile.view.second.SecondActivity
import com.habibfr.suitmedia_test_mobile.view.second.SharedViewModel

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private lateinit var adapter: UserAdapter

    private val thirdViewModel: ThirdViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    private val sharedViewModel: SharedViewModel by viewModels {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThirdBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            setSupportActionBar(topAppBar)
            topAppBar.setNavigationOnClickListener {
                onBackPressed()

            }

            val layoutManager = LinearLayoutManager(this@ThirdActivity)
            rvUser.layoutManager = layoutManager
            val itemDecoration =
                DividerItemDecoration(this@ThirdActivity, layoutManager.orientation)
            rvUser.addItemDecoration(itemDecoration)
            rvUser.setHasFixedSize(true);
        }

        getUsers()
    }

    private fun getUsers() {
        thirdViewModel.users.observe(this) { result ->
            if (result != null) {
                when (result) {
                    is Result.Loading -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }

                    is Result.Success -> {
                        setupAdapter(result.data.data?.toMutableList())
                        binding.progressBar.visibility = View.GONE
                    }

                    is Result.Error -> {
                        binding.progressBar.visibility = View.GONE

                        Snackbar.make(
                            binding.root, result.error, Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    private fun setupAdapter(users: MutableList<DataUser?>?) {
        with(binding) {
            if (users != null) {
                if (users.isEmpty()) {

                    Snackbar.make(
                        this@ThirdActivity, root, "Data Users empty...", Snackbar.LENGTH_SHORT
                    ).show()
                } else {
                    adapter = UserAdapter()
                    adapter.submitList(users)
                    adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {

                        override fun onItemClicked(user: DataUser) {
                            user.firstName?.let {
                                user.lastName?.let { it1 ->
                                    chooseUser(
                                        it,
                                        it1
                                    )
                                }
                            }
                        }
                    })
                    rvUser.adapter = adapter
                }
            }
        }
    }

    private fun chooseUser(firstName: String, lastName: String) {
        val intent = Intent(this@ThirdActivity, SecondActivity::class.java)
        val chooseUser = "$firstName $lastName"
        sharedViewModel.setSelectedUser(chooseUser)

        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}