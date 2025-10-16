package br.com.supermercado.estoque.domain.validation

import br.com.supermercado.estoque.application.port.output.ProductRepositoryPort
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.BrandRepositoryAdapter
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.ProductRepositoryAdapter
import java.util.*

abstract class ProductValidator {
    fun verifyIfExistNameAlrighRegistred(
        productName: String,
        repository: ProductRepositoryAdapter
    ): Boolean {
        return repository.existsByName(productName)
    }

    fun verifyIfBrandIdExist(
        brandId: UUID,
        brandRepository: BrandRepositoryAdapter
    ): Boolean {
        return brandRepository.existsById(brandId)
    }
}