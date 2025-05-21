package com.example.eraassignment.model

data class Image(
    val src: ImageSource,
    val url: String,
    val alt: String,
    val id: Int
)

data class ImageSource (
    val original: String,
    val large: String,
    val large2x: String,
    val medium: String,
    val small: String
)

data class SearchResponse(
    val photos: List<Image>,
    val total_results: Int,
    val status: Int?,
    val code: String?
)
