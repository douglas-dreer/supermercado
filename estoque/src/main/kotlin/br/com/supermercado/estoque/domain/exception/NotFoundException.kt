package br.com.supermercado.estoque.domain.exception

/**
 * Base class for all business exceptions that indicate a requested resource was not found.
 * Results in an HTTP 404 Not Found response.
 */
open class NotFoundException(message: String) : RuntimeException(message)