package br.com.supermercado.estoque.application.port.input.category

import br.com.supermercado.estoque.application.dto.query.FindAllProductsQuery
import br.com.supermercado.estoque.domain.model.Product
import org.springframework.data.domain.Page

interface FindAllCategoriesUseCase {
    fun execute(query: FindAllProductsQuery): Page<Product>
}