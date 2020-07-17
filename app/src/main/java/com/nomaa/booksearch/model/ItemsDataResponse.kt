package com.nomaa.booksearch.model

/**
 * Data class for holding the "items" data object in the books API JSON response which holds a
 * "volumeInfo object
 */
data class ItemsDataResponse(var volumeInfo: VolumeInfoDataResponse)