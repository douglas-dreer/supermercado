package br.com.supermercado.estoque.application.usecase.brand

import br.com.supermercado.estoque.application.port.input.brand.DeleteBrandUseCase
import br.com.supermercado.estoque.application.port.input.brand.validate.DeleteBrandValidate
import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.infrastructure.common.annotation.UseCase
import java.util.UUID

@UseCase
class DeleteBrandUseCaseImpl(
    private val repository: BrandRepositoryPort,
    private val validator: DeleteBrandValidate,
) : DeleteBrandUseCase {
    override fun execute(brandId: UUID): Unit {
        validator.execute(brandId)
        repository.deleteById(brandId)
    }
}