package com.solarabehety.intivechallenge.ui.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import com.solarabehety.intivechallenge.IntiveApplication
import com.solarabehety.intivechallenge.R
import com.solarabehety.intivechallenge.repository.AppRepository
import com.solarabehety.intivechallenge.ui.fragments.UsersListFragment
import com.solarabehety.intivechallenge.utils.Utils
import com.solarabehety.intivechallenge.utils.replaceFragment
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        replaceFragment(UsersListFragment(), R.id.fragment_container)

        AppRepository.getInstance(compositeDisposable).findUsers()
        if (!Utils.checkInternetConnection(IntiveApplication.applicationContext()))
            showNoConnectionDialog()
    }

    private fun showNoConnectionDialog() {
        val snackbar = Snackbar.make(rootView, R.string.no_connection, Snackbar.LENGTH_LONG)
        snackbar.setAction(R.string.action_accept) { snackbar.dismiss() }
        snackbar.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.clear()
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.title = getString(R.string.app_name)
    }


}
