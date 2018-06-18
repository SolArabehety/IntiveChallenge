package com.solarabehety.intivechallenge.ui.fragments

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment
import android.support.v4.util.Pair
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.solarabehety.intivechallenge.utils.Constants
import com.solarabehety.intivechallenge.viewmodel.UsersListViewModel
import com.solarabehety.intivechallenge.R
import com.solarabehety.intivechallenge.R.id.*
import com.solarabehety.intivechallenge.model.User
import com.solarabehety.intivechallenge.ui.activities.MainActivity
import com.solarabehety.intivechallenge.ui.activities.ProfileActivity
import com.solarabehety.intivechallenge.ui.adapters.GridItemDecoration
import com.solarabehety.intivechallenge.ui.adapters.UsersRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_profile_list.*


/**
 * Created by Sol Arabehety on 6/12/2018.
 */
class UsersListFragment : Fragment() {
    private lateinit var viewModel: UsersListViewModel
    private val onApplicationClickListener = View.OnClickListener { v ->
        startProfileActivity(v, v.tag as User)
    }

    private val adapter = UsersRecyclerViewAdapter(onApplicationClickListener)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_profile_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = usersViewModel()
        viewModel.users.observe(this, Observer<List<User>> {
            Log.i("OBSERVER", "OK")
            updateUI(it)
        })
    }


    private fun usersViewModel(): UsersListViewModel {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return UsersListViewModel((activity as MainActivity).compositeDisposable) as T
            }
        }

        return ViewModelProviders.of(this, factory).get(UsersListViewModel::class.java)
    }

    private fun updateUI(users: List<User>?) {

        if (users != null && !users.isEmpty()) {
            adapter.setData(users)
            if (adapter.itemCount == 0)
                showNoUsersLayout(true) else showNoUsersLayout(false)

        } else showNoUsersLayout(true)
    }

    private fun showNoUsersLayout(show: Boolean) {
        if (show) {
            tvNoData.visibility = View.VISIBLE
            btnRetry.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
        } else {
            tvNoData.visibility = View.GONE
            btnRetry.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
        }
    }

    private fun initViews() {
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(GridItemDecoration(resources.getDimensionPixelOffset(R.dimen.grid_divider)))
        setupScrollListener()

        btnRetry.setOnClickListener { _ -> viewModel.findUsers() }
    }


    private fun setupScrollListener() {
        val layoutManager = recyclerView.layoutManager as GridLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = layoutManager.itemCount
                val visibleItemCount = layoutManager.childCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                viewModel.listScrolled(visibleItemCount, lastVisibleItem, totalItemCount)
            }
        })
    }

    private fun startProfileActivity(view: View, selectedUser: User) {
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity as Activity,

                Pair<View, String>(view.findViewById(R.id.ivProfile), Constants.PICTURE),
                Pair<View, String>(view.findViewById(R.id.patternBackground), Constants.PATTERN_BACKGROUND),
                Pair<View, String>(view.findViewById(R.id.tvCompleteName), Constants.NAME),
                Pair<View, String>(view.findViewById(R.id.rootView), Constants.BACKGROUND))

        val intent = Intent(activity, ProfileActivity::class.java)
        intent.putExtra(Constants.SELECTED_USER, Gson().toJson(selectedUser))
        intent.putExtra(Constants.USER_COLOR, (view.background as ColorDrawable).color)
        ActivityCompat.startActivity(activity as Context, intent, activityOptions.toBundle())
    }

}