package com.jade.titanflex.api.data

data class wgerImageResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<ResultX>
)