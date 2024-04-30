package com.jade.titanflex.api.data

data class wgerResponse(
    val count: Int,
    val next: String,
    val previous: Any,
    val results: List<Result>
)