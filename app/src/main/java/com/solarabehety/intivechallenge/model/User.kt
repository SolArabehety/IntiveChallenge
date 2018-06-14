package com.solarabehety.intivechallenge.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by Sol Arabehety on 6/11/2018.
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


//{
//    "name": {
//    "title": "miss",
//    "first": "louane",
//    "last": "vidal"
//},
//    "email": "louane.vidal@example.com",
//    "login": {
//    "username": "silverbear716",
//    "password": "mybaby",
//    "salt": "VB5ywSDU",
//    "md5": "8192a266aeebd343adc5051573ca9d8f",
//    "sha1": "b7589333acbac160f52ec9a1fbcbe04e3599072d",
//    "sha256": "ab5b7e6e452fbd5cddea491a16a020aa7e36578c431cc8c7c6e6ea8962161acc"
//},
//    "id": {
//    "name": "INSEE",
//    "value": "2NNaN10177623 61"
//},
//    "picture": {
//    "large": "https://randomuser.me/api/portraits/women/79.jpg",
//    "medium": "https://randomuser.me/api/portraits/med/women/79.jpg",
//    "thumbnail": "https://randomuser.me/api/portraits/thumb/women/79.jpg"
//}
//},