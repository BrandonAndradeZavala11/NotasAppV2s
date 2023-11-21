package com.example.marsphotos.model

import kotlinx.serialization.Serializable

@Serializable
data class Nota(
    val id:         Int,
    val titulo:     String,
    val contenido:  String,
    val estatus:    Int,
    val tipo:       Int,
    val ubicacion: String,
    val fecha:      String,
    val fechaModi:  String,
    val fechaCum:   String
    )


@Serializable
data class NotaDT(
    val titulo:     String,
    val contenido:  String,
    val estatus:    Int,
    val tipo:       Int,
    val ubicacion: String,
    val fecha:      String,
    val fechaModi:  String,
    val fechaCum:   String
)
