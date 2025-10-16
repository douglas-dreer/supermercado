package br.com.supermercado.estoque.infrastructure.adapter.input.rest.exception

import java.time.LocalDateTime

/**
 * Standard DTO for error responses.
 */
data class ErrorResponse(
    val timestamp: LocalDateTime,
    val status: Int,
    val error: String,
    val message: String,
    val path: String,
    val details: Map<String, String>? = null
)