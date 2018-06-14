package com.solarabehety.intivechallenge.repository.networking

import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Response
import okhttp3.ResponseBody
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.google.gson.Gson
import com.solarabehety.intivechallenge.model.User


/**
 * Created by Sol Arabehety on 6/12/2018.
 */
class APIUsersResponseInterceptor : Interceptor {
    private val mJSON = MediaType.parse("application/json; charset=utf-8")
    private val mGSON = Gson()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        val body = response.body()

        body?.let {
            val finalJson = parseUsersOriginalJSON(it.string())
            body.close()
            val newResponse = response.newBuilder().body(ResponseBody.create(mJSON, finalJson))
            return newResponse.build()
        }

        return response
    }

    private fun parseUsersOriginalJSON(originalJson: String): String {
        val users = mutableListOf<User>()

        val parser = JsonParser()
        val usersJson = parser.parse(originalJson).asJsonObject
        val usersArray = usersJson.getAsJsonArray("results")

        for (drink in usersArray) {
            val user = parseUserObject(drink.asJsonObject)
            user?.let { users.add(it) }
        }

        return mGSON.toJson(users)
    }

    private fun parseUserObject(jsonObject: JsonObject): User? {
        try {
            val user = User(jsonObject.get("id").asJsonObject.get("value").asString)
            user.firstName = jsonObject.get("name").asJsonObject.get("first").asString
            user.lastName = jsonObject.get("name").asJsonObject.get("last").asString
            user.email = jsonObject.get("email").asString
            user.username = jsonObject.get("login").asJsonObject.get("username").asString
            user.picture = jsonObject.get("picture").asJsonObject.get("large").asString
            user.thumbnail = jsonObject.get("picture").asJsonObject.get("thumbnail").asString
            return user
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }
}