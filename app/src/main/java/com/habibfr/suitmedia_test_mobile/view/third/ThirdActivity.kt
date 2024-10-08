package com.habibfr.suitmedia_test_mobile.view.third

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.habibfr.suitmedia_test_mobile.data.paging.LoadingStateAdapter
import com.habibfr.suitmedia_test_mobile.data.remote.api.response.User
import com.habibfr.suitmedia_test_mobile.databinding.ActivityThirdBinding
import com.habibfr.suitmedia_test_mobile.view.adapter.UserAdapter
import com.habibfr.suitmedia_test_mobile.view.factory.ViewModelFactory
import com.habibfr.suitmedia_test_mobile.view.second.SecondActivity
import com.habibfr.suitmedia_test_mobile.view.second.SharedViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ThirdActivity : AppCompatActivity() {
    private lateinit var binding: ActivityThirdBinding
    private lateinit var userAdapter: UserAdapter

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

            userAdapter = UserAdapter()
            userAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(user: User) {
                    user.firstName?.let {
                        user.lastName?.let { it1 ->
                            chooseUser(
                                it, it1
                            )
                        }
                    }
                }
            })

            rvUser.layoutManager = LinearLayoutManager(this@ThirdActivity)
            rvUser.adapter = userAdapter.withLoadStateFooter(
                footer = LoadingStateAdapter { userAdapter.retry() }
            )

            getData()

            srlRefresh.setOnRefreshListener {
                getData()
                srlRefresh.isRefreshing = false
            }
        }
    }

    private fun getData() {
        binding.progressBar.visibility = View.VISIBLE

        lifecycleScope.launch {
            delay(1000)
            thirdViewModel.usersPaginate.observe(this@ThirdActivity) { pagingData ->
                userAdapter.submitData(lifecycle, pagingData)
                binding.progressBar.visibility = View.GONE
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