package br.com.supermercado.estoque.application.port.input.product

import br.com.supermercado.estoque.application.dto.query.FindAllProductsQuery
import br.com.supermercado.estoque.domain.model.Product
import org.springframework.data.domain.Page

interface FindAllProductsUseCase {
    fun execute(query: FindAllProductsQuery): Page<Product>
}