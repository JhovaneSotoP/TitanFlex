package com.jade.titanflex.api.data

data class ResultX(
    val author_history: List<Any>,
    val exercise_base: Int,
    val exercise_base_uuid: String,
    val id: Int,
    val image: String,
    val is_main: Boolean,
    val license: Int,
    val license_author: String,
    val license_author_url: String,
    val license_derivative_source_url: String,
    val license_object_url: String,
    val license_title: String,
    val style: String,
    val uuid: String
)