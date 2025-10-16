package br.com.supermercado.estoque.application.port.input.brand

import br.com.supermercado.estoque.application.dto.command.UpdateBrandCommand
import br.com.supermercado.estoque.domain.model.Brand

interface UpdateBrandUseCase {
    fun execute(command: UpdateBrandCommand): Brand
    fun executeValidate(brand: Brand)
}