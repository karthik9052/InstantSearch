package com.karthik.reloyassignment.data.response


data class ApiResponse(
    val hits: List<Hit>,
    val total: Int,
    val totalHits: Int
)