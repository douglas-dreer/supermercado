package br.com.supermercado.estoque.application.port.input.brand

import br.com.supermercado.estoque.application.dto.query.FindAllBrandsQuery
import br.com.supermercado.estoque.domain.model.Brand
import org.springframework.data.domain.Page

interface FindAllBrandsUseCase {
    fun execute(query: FindAllBrandsQuery): Page<Brand>
}