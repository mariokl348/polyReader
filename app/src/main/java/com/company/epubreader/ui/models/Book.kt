package com.company.epubreader.ui.models

data class Book(
    val id: Int,
    val title: String,
    val author: String,
    val image: String,
    val summary: String,
    val year: Int,
    val publisher: String,
    val narrator: String,
    val category: String,
    val epub: String,
    val audio: String
)