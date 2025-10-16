package br.com.supermercado.estoque.domain.validation

interface ValidationStrategy<T> {
    fun execute(item: T)
}