package br.com.supermercado.estoque.application.validate.brand

import br.com.supermercado.estoque.infrastructure.common.annotation.DeleteValidation
import br.com.supermercado.estoque.domain.exception.BrandNotFoundException
import br.com.supermercado.estoque.domain.validation.BrandValidator
import br.com.supermercado.estoque.domain.validation.ValidationStrategy
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter.BrandRepositoryAdapter
import org.springframework.stereotype.Component
import java.util.*

@Component
@DeleteValidation
class DeleteBrandValidate(
    private val repository: BrandRepositoryAdapter
): BrandValidator(), ValidationStrategy<UUID> {
    override fun execute(item: UUID) {
        require(verifyIfExistId(item, repository)) {
            throw BrandNotFoundException(item)
        }
    }
}