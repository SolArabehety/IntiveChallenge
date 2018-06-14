package com.solarabehety.intivechallenge.ui.activities

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewCompat
import android.support.v7.app.ActionBar
import android.view.MenuItem
import com.google.gson.Gson
import com.solarabehety.intivechallenge.R
import com.solarabehety.intivechallenge.model.User
import com.solarabehety.intivechallenge.utils.Constants
import com.solarabehety.intivechallenge.viewmodel.UserProfileViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {
    private lateinit var model: UserProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        ViewCompat.setTransitionName(ivProfile, Constants.PICTURE)
        ViewCompat.setTransitionName(backgroundColor, Constants.BACKGROUND)

        val userString = intent.getStringExtra(Constants.SELECTED_USER) ?: ""
        val userColor = intent.getIntExtra(Constants.USER_COLOR, 0)
        val selectedUser = Gson().fromJson(userString, User::class.java)

        selectedUser?.let {
            model = userProfileViewModel(it)
            initUI(userColor)
        }

        initToolbar()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            this.title = ""
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
        }
    }


    private fun initUI(userColor: Int) {
        if (userColor != 0)
            backgroundColor.setBackgroundColor(userColor)

        Picasso.get().load(model.user.picture).into(ivProfile)

        val completeName = model.user.firstName.capitalize() + " " + model.user.lastName.capitalize()
        tvNameComplete.text = completeName

        tvUsername.text = model.user.username
        tvEmail.text = model.user.email
    }


    private fun userProfileViewModel(selectedUser: User): UserProfileViewModel {
        val factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return UserProfileViewModel(selectedUser) as T
            }
        }

        return ViewModelProviders.of(this, factory).get(UserProfileViewModel::class.java)
    }
}
