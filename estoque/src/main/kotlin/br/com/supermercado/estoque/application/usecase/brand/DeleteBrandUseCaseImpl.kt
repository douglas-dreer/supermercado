package br.com.supermercado.estoque.application.usecase.brand

import br.com.supermercado.estoque.application.port.input.brand.DeleteBrandUseCase
import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.infrastructure.common.annotation.DeleteValidation

import br.com.supermercado.estoque.domain.validation.ValidationStrategy
import br.com.supermercado.estoque.infrastructure.common.annotation.UseCase
import java.util.*

@UseCase
class DeleteBrandUseCaseImpl(
    private val repository: BrandRepositoryPort,
    @DeleteValidation
    private val validators: List<ValidationStrategy<UUID>>

) : DeleteBrandUseCase {
    override fun execute(brandId: UUID) {
        executeValidate(brandId)
        repository.deleteById(brandId)
    }

    override fun executeValidate(brandId: UUID) {
        validators.forEach { it.execute(brandId) }
    }
}