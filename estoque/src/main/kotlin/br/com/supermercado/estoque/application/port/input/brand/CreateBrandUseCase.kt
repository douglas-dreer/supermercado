package br.com.supermercado.estoque.application.port.input.brand

import br.com.supermercado.estoque.application.dto.command.CreateBrandCommand
import br.com.supermercado.estoque.domain.model.Brand

interface CreateBrandUseCase {
    fun execute(command: CreateBrandCommand): Brand
    fun executeValidate(brand: Brand)
}






