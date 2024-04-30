package com.jade.titanflex.api.data

data class Result(
    val author_history: List<String>,
    val category: Int,
    val created: String,
    val description: String,
    val equipment: List<Int>,
    val exercise_base: Int,
    val id: Int,
    val language: Int,
    val license: Int,
    val license_author: String,
    val muscles: List<Int>,
    val muscles_secondary: List<Int>,
    val name: String,
    val uuid: String,
    val variations: List<Int>
)