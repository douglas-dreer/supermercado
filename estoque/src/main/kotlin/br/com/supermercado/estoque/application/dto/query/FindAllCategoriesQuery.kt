package br.com.supermercado.estoque.application.dto.query

import org.springframework.data.domain.Pageable

data class FindAllCategoriesQuery(
    val pageable: Pageable
)