package br.com.supermercado.estoque.application.usecase.brand

import br.com.supermercado.estoque.application.dto.command.CreateBrandCommand
import br.com.supermercado.estoque.application.dto.mapper.BrandCommandMapper
import br.com.supermercado.estoque.application.port.input.brand.CreateBrandUseCase
import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.infrastructure.common.annotation.CreateValidation
import br.com.supermercado.estoque.domain.model.Brand
import br.com.supermercado.estoque.domain.validation.ValidationStrategy
import br.com.supermercado.estoque.infrastructure.common.annotation.UseCase

@UseCase
class CreateBrandUseCaseImpl(
    private val brandRepository: BrandRepositoryPort,
    private val converter: BrandCommandMapper,
    @CreateValidation
    private val validators: List<ValidationStrategy<Brand>>

) : CreateBrandUseCase {

    override fun execute(command: CreateBrandCommand): Brand {
        val brand = converter.toDomain(command)
        executeValidate(brand)
        return brandRepository.save(brand)
    }

    override fun executeValidate(brand: Brand) {
        validators.forEach { it.execute(brand) }
    }
}