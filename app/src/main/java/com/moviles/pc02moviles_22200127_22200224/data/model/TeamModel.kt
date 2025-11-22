package com.moviles.pc02moviles_22200127_22200224.data.model

data class TeamModel(
    val id: String = "",
    val nombreEquipo: String = "",
    val anioFundacion: String = "",
    val nombreEstadio: String = "",
    val urlImagenEquipo: String = ""
) {
    constructor() : this("", "", "", "", "")

    fun toMap(): Map<String, Any> {
        return mapOf(
            "nombreEquipo" to nombreEquipo,
            "anioFundacion" to anioFundacion,
            "nombreEstadio" to nombreEstadio,
            "urlImagenEquipo" to urlImagenEquipo
        )
    }
}