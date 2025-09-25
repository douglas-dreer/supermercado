package br.com.supermercado.estoque.application.port.input.product

import br.com.supermercado.estoque.application.dto.query.FindProductByBarcodeQuery
import br.com.supermercado.estoque.domain.model.Product

interface FindProductByBarcodeUseCase {
    fun execute(query: FindProductByBarcodeQuery): Product
}
