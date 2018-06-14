package com.solarabehety.intivechallenge.ui.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.view.View
import com.jbsolutions.iwoapp.ui.activities.BaseActivity
import com.solarabehety.intivechallenge.R
import com.solarabehety.intivechallenge.repository.AppRepository
import com.solarabehety.intivechallenge.repository.networking.UsersCallback
import com.solarabehety.intivechallenge.ui.fragments.UsersListFragment
import com.solarabehety.intivechallenge.utils.replaceFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), UsersCallback {
    private val mProfileListFragment = UsersListFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        addDisposable(AppRepository.instance.getUsers(this))
        initToolbar()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.title = getString(R.string.app_name)
    }

    override fun onSuccess() {
        initUI()
    }

    override fun onError(error: String) {
        val snackbar = Snackbar.make(rootView, error, Snackbar.LENGTH_SHORT)
        snackbar.setAction(R.string.action_accept, { snackbar.dismiss() })
        snackbar.show()

        initUI()
    }


    private fun initUI() {
        progressBar.visibility = View.GONE
        replaceFragment(mProfileListFragment, R.id.fragment_container)
    }
}
