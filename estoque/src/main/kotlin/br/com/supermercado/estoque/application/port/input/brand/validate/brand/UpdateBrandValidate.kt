package br.com.supermercado.estoque.application.port.input.brand.validate.brand

import br.com.supermercado.estoque.domain.model.Brand

interface UpdateBrandValidate {
    fun execute(brand: Brand)
}