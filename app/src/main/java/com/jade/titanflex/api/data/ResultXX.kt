package com.jade.titanflex.api.data

data class ResultXX(
    val category: Int,
    val created: String,
    val equipment: List<Int>,
    val id: Int,
    val last_update: String,
    val license_author: String,
    val muscles: List<Int>,
    val muscles_secondary: List<Int>,
    val uuid: String,
    val variations: Int
)