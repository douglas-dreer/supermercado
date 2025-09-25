package br.com.supermercado.estoque.application.dto.query


import org.springframework.data.domain.Pageable
import java.util.UUID

data class FindAllProductsQuery(
    val pageable: Pageable,
    val brandId: UUID? = null,
    val category: String? = null,
    val name: String? = null
)