package com.dicoding.livestory.util

import com.dicoding.livestory.model.local.EntityStory
import com.dicoding.livestory.model.response.*

/**
 * Created by Rahmat Hidayat on 05/11/2022.
 */
object DataDummy {
    fun generateDummyUserLocation(): List<ListStory> {
        val markerList = ArrayList<ListStory>()
        for (i in 0..10) {
            val marker = ListStory(
                "1",
                "Rahmat hidayat",
                "AndroidDeveloper",
                "https://www.google.com",
                "05-11-00",
                -6.8331128,
                107.6048483

            )
            markerList.add(marker)
        }
        return markerList
    }

    fun generateDummyLoginResponse(): LoginResult {
        return LoginResult(
            false,
            "Success",
            generateDummyLoginData()
        )
    }

    fun generateDummyRegisterReponse(): RegisterResult {
        return RegisterResult(
            false,
            "Success",
        )
    }

    private fun generateDummyLoginData(): LoginData {
        return LoginData(
            "1",
            "Rahmat hidayat",
            "Bearer",
        )
    }

    fun generateUploadDataResponse(): UploadDataResponse {
        return UploadDataResponse(
            false,
            "success"
        )
    }

    fun generateDummyStoryResponse(): List<EntityStory> {
        val items: MutableList<EntityStory> = arrayListOf()
        for (i in 0..100) {
            val story = EntityStory(
                i.toString(),
                "name + $i",
                "description $i",
                "www.google.com",
                "17/03/00"
            )
            items.add(story)
        }
        return items
    }
}