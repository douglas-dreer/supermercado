package br.com.supermercado.estoque.application.port.input.brand.validate

import java.util.UUID

interface DeleteBrandValidate {
    fun execute(brandId: UUID)
}