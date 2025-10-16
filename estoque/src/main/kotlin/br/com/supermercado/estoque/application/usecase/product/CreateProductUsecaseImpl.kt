package br.com.supermercado.estoque.application.usecase.product

import br.com.supermercado.estoque.application.dto.command.CreateProductCommand
import br.com.supermercado.estoque.application.dto.mapper.ProductCommandMapper
import br.com.supermercado.estoque.application.port.input.brand.validate.product.CreateProductValidate
import br.com.supermercado.estoque.application.port.input.product.CreateProductUseCase
import br.com.supermercado.estoque.application.port.output.ProductRepositoryPort
import br.com.supermercado.estoque.domain.model.Product
import br.com.supermercado.estoque.domain.validation.ValidationStrategy
import br.com.supermercado.estoque.infrastructure.common.annotation.CreateValidation
import br.com.supermercado.estoque.infrastructure.common.annotation.UseCase

@UseCase
class CreateProductUsecaseImpl(
    private val productRepository: ProductRepositoryPort,
    private val converter: ProductCommandMapper,
    @CreateValidation
    private val validators: List<ValidationStrategy<Product>>
): CreateProductUseCase {
    override fun execute(command: CreateProductCommand): Product {
        val product = converter.toDomain(command)
        executeValidate(product)
        return productRepository.save(product)
    }

    override fun executeValidate(product: Product) {
        validators.forEach { it.execute(product) }
    }
}