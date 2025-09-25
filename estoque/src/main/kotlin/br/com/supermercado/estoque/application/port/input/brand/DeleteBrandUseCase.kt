package br.com.supermercado.estoque.application.port.input.brand

import java.util.UUID

interface DeleteBrandUseCase {
    fun execute(brandId: UUID)
}