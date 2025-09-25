package br.com.supermercado.estoque.domain.exeception


abstract class DomainException(message: String, cause: Throwable? = null) : RuntimeException(message, cause)
