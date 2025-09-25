package br.com.supermercado.estoque.application.usecase.brand

import br.com.supermercado.estoque.application.dto.query.FindAllBrandsQuery
import br.com.supermercado.estoque.application.port.input.brand.FindAllBrandsUseCase
import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.domain.model.Brand
import br.com.supermercado.estoque.infrastructure.common.annotation.UseCase
import org.springframework.data.domain.Page

@UseCase
class FindAllBrandsUseCaseImpl(
    final val repository: BrandRepositoryPort
): FindAllBrandsUseCase {
    override fun execute(query: FindAllBrandsQuery): Page<Brand> {
        return repository.findAll(query.pageable)
    }
}