package br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.response

import java.time.LocalDateTime

data class ErrorResponse(
    val message: String,
    val timestamp: LocalDateTime = LocalDateTime.now(),
    val details: List<String> = emptyList()
)