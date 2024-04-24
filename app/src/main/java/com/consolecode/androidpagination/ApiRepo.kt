package com.consolecode.androidpagination


import retrofit2.http.GET
import retrofit2.http.Query

interface ApiRepo {
    @GET("content/misc/media-coverages")
    suspend fun getMediaCoverages(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
    ):
            List<MediaCoverage>
}