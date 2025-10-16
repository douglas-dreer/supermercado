package br.com.supermercado.estoque.domain.validation

import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.BrandRepositoryAdapter
import java.util.*

abstract class BrandValidator {
    fun verifyIfExistId(brandId: UUID, repository: BrandRepositoryAdapter): Boolean {
        return repository.existsById(brandId)
    }

    fun isNameRegisteredForOtherBrand(brandName: String, repository: BrandRepositoryAdapter): Boolean {
        return repository.existsByName(brandName)
    }
}