package br.com.supermercado.estoque.application.usecase.brand

import br.com.supermercado.estoque.application.dto.command.UpdateBrandCommand
import br.com.supermercado.estoque.application.dto.mapper.BrandCommandMapper
import br.com.supermercado.estoque.application.port.input.brand.UpdateBrandUseCase
import br.com.supermercado.estoque.application.port.input.brand.validate.UpdateBrandValidate
import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.domain.model.Brand
import br.com.supermercado.estoque.infrastructure.common.annotation.UseCase

@UseCase
class UpdateBrandUseCaseImpl (
    private val repository: BrandRepositoryPort,
    private val converter: BrandCommandMapper,
    private val validator: UpdateBrandValidate,
): UpdateBrandUseCase {
    override fun execute(command: UpdateBrandCommand): Brand {
        val brand = converter.toDomain(command)
        return repository.update(brand)
    }
}