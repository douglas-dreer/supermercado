package br.com.supermercado.estoque.application.usecase.brand

import br.com.supermercado.estoque.application.dto.command.CreateBrandCommand
import br.com.supermercado.estoque.application.dto.mapper.BrandCommandMapper
import br.com.supermercado.estoque.application.port.input.brand.CreateBrandUseCase
import br.com.supermercado.estoque.application.port.input.brand.validate.CreateBrandValidate
import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.domain.model.Brand
import br.com.supermercado.estoque.infrastructure.common.annotation.UseCase

@UseCase
class CreateBrandUseCaseImpl(
    private val brandRepository: BrandRepositoryPort,
    private val converter: BrandCommandMapper,
    private val validator: CreateBrandValidate
) : CreateBrandUseCase {

    override fun execute(command: CreateBrandCommand): Brand {
        val brand = converter.toDomain(command)
        validator.execute(brand)
        return brandRepository.save(brand)
    }
}