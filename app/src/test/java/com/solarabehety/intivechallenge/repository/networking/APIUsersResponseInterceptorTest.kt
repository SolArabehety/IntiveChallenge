package com.solarabehety.intivechallenge.repository.networking

import com.google.gson.JsonParser
import org.junit.Assert
import org.junit.Test
import java.io.BufferedReader
import java.io.FileInputStream
import java.io.IOException
import java.io.InputStreamReader

/**
 * Created by Sol Arabehety on 6/18/2018.
 */
class APIUsersResponseInterceptorTest {
    val ASSET_BASE_PATH = "../app/src/test/resources/"

    @Test
    fun parseUserObject_NormalUseCase() {
        // GIVEN
        val normalUseCaseUserListJSON = readJsonFile("users_list_input.json")
        val json = JsonParser().parse(normalUseCaseUserListJSON).asJsonObject

        // WHEN
        val userResult = APIUsersResponseInterceptor().parseUserObject(json)

        // THEN
        Assert.assertEquals(userResult?.id, "9f07341f-c7e6-45b7-bab0-af6de5a4582d")
        Assert.assertEquals(userResult?.username, "angryostrich988")
        Assert.assertEquals(userResult?.firstName, "louane")
        Assert.assertEquals(userResult?.lastName, "vidal")
        Assert.assertEquals(userResult?.email, "louane.vidal@example.com")
        Assert.assertEquals(userResult?.picture, "https://randomuser.me/api/portraits/women/88.jpg")
        Assert.assertEquals(userResult?.thumbnail, "https://randomuser.me/api/portraits/thumb/women/88.jpg")
    }

    @Throws(IOException::class)
    private fun readJsonFile(filename: String): String {
        val br = BufferedReader(InputStreamReader(FileInputStream(ASSET_BASE_PATH + filename)))
        val sb = StringBuilder()
        var line: String? = br.readLine()
        while (line != null) {
            sb.append(line)
            line = br.readLine()
        }

        return sb.toString()
    }
}