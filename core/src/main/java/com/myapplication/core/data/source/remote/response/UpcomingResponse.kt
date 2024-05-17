package com.myapplication.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UpcomingResponse(

    @field:SerializedName("dates")
    val dates: Dates,

    @field:SerializedName("page")
    val page: Int,

    @field:SerializedName("total_pages")
    val totalPages: Int,

    @field:SerializedName("results")
    val results: List<MovieItemResponse>,

    @field:SerializedName("total_results")
    val totalResults: Int,
)
