package br.com.supermercado.estoque.application.port.input.brand.validate

import br.com.supermercado.estoque.domain.model.Brand

interface CreateBrandValidate {
    fun execute(brand: Brand)
}