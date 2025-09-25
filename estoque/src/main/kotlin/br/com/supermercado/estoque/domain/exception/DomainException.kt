package br.com.supermercado.estoque.domain.exception


abstract class DomainException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)
