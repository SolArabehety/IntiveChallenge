package com.solarabehety.intivechallenge.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Sol Arabehety on 6/17/2018.
 */
@Entity
data class User(@PrimaryKey var id: String) {
    var username: String = ""
    var firstName: String = ""
    var lastName: String = ""
    var email: String = ""
    var picture: String = ""
    var thumbnail: String = ""
}
