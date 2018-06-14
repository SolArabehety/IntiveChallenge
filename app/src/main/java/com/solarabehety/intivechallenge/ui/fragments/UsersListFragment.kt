package com.solarabehety.intivechallenge.ui.fragments

import android.app.Activity
import android.arch.lifecycle.Observer
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
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.solarabehety.intivechallenge.utils.Constants
import com.solarabehety.intivechallenge.viewmodel.ProfileListViewModel
import com.solarabehety.intivechallenge.R
import com.solarabehety.intivechallenge.model.User
import com.solarabehety.intivechallenge.ui.activities.ProfileActivity
import com.solarabehety.intivechallenge.ui.adapters.GridItemDecoration
import com.solarabehety.intivechallenge.ui.adapters.MyWorkOrdersRecyclerViewAdapter
import kotlinx.android.synthetic.main.activity_profile.view.*
import kotlinx.android.synthetic.main.fragment_profile_list.*


/**
 * Created by Sol Arabehety on 6/12/2018.
 */
class UsersListFragment : Fragment() {
    private val onApplicationClickListener = View.OnClickListener { v ->
        startProfileActivity(v, v.tag as User)
    }

    private val adapter = MyWorkOrdersRecyclerViewAdapter(onApplicationClickListener)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_profile_list, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.adapter = adapter
        recyclerView.addItemDecoration(GridItemDecoration(resources.getDimensionPixelOffset(R.dimen.grid_divider)))
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val mViewModel = ViewModelProviders.of(this).get(ProfileListViewModel::class.java)
        mViewModel.users.observe(this, Observer<List<User>> {
            updateUI(it)
        })
    }

    private fun updateUI(users: List<User>?) {
        if (users != null && !users.isEmpty()) {
            tvNoData.visibility = View.GONE
            adapter.setData(users)
            if (adapter.itemCount == 0)
                tvNoData.visibility = View.VISIBLE
        } else
            tvNoData.visibility = View.VISIBLE
    }


    private fun startProfileActivity(view: View, selectedUser: User) {
        val activityOptions = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity as Activity,

                Pair<View, String>(view.findViewById(R.id.ivProfile), Constants.PICTURE),
                Pair<View, String>(view.findViewById(R.id.rootView), Constants.BACKGROUND))

        val intent = Intent(activity, ProfileActivity::class.java)
        intent.putExtra(Constants.SELECTED_USER, Gson().toJson(selectedUser))
        intent.putExtra(Constants.USER_COLOR, (view.background as ColorDrawable).color)
        ActivityCompat.startActivity(activity as Context, intent, activityOptions.toBundle())
    }

}