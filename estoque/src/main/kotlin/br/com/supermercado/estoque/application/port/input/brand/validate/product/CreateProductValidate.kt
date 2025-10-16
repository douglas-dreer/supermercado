package br.com.supermercado.estoque.application.port.input.brand.validate.product

import br.com.supermercado.estoque.domain.model.Product
import org.springframework.stereotype.Component

@Component
interface CreateProductValidate {
    fun execute(product: Product)
}
