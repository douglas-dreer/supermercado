package br.com.supermercado.estoque.domain.exception

/**
 * Base class for general business rule violations, such as a resource that already exists.
 * Results in an HTTP 409 Conflict response.
 */
open class BusinessException(message: String) : RuntimeException(message)