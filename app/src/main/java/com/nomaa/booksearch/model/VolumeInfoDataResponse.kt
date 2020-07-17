package com.nomaa.booksearch.model

/**
 * Data class for holding a "volumeInfo" data object in the books API JSON response. Each volumeInfo
 * object contains a title, author, and a "imageLinks" data object with URL of the thumbnail image
 */
data class VolumeInfoDataResponse(
    val title: String,
    val authors: List<String>,
    val imageLinks: ImageLinksDataResponse?
)
