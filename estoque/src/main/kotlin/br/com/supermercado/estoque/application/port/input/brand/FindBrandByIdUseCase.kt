package br.com.supermercado.estoque.application.port.input.brand

import br.com.supermercado.estoque.application.dto.query.FindBrandByIdQuery
import br.com.supermercado.estoque.domain.model.Brand

interface FindBrandByIdUseCase {
    fun execute(query: FindBrandByIdQuery): Brand?
}