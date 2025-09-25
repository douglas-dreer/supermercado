package br.com.supermercado.estoque.application.usecase.brand

import br.com.supermercado.estoque.application.dto.query.FindBrandByIdQuery
import br.com.supermercado.estoque.application.port.input.brand.FindBrandByIdUseCase
import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.domain.model.Brand
import br.com.supermercado.estoque.infrastructure.common.annotation.UseCase

@UseCase
class FindBrandByIdUseCaseImpl(
    final val repository: BrandRepositoryPort
): FindBrandByIdUseCase {
    override fun execute(query: FindBrandByIdQuery): Brand? {
        return repository.findById(query.id)
    }
}