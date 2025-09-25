package br.com.supermercado.estoque.infrastructure.providers

import br.com.supermercado.estoque.application.dto.command.CreateBrandCommand
import br.com.supermercado.estoque.application.dto.command.UpdateBrandCommand
import br.com.supermercado.estoque.domain.model.Brand
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request.CreateBrandRequest
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request.UpdateBrandRequest
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.entity.BrandEntity
import org.springframework.stereotype.Component
import java.time.LocalDateTime
import java.util.*

/**
 * Fornece dados de teste para a entidade Brand.
 * Esta classe é usada em testes para fornecer instâncias consistentes e reutilizáveis de objetos relacionados à Marca.
 */
@Component
class BrandProvider {
    private val BRAND_ID: UUID = UUID.randomUUID()
    private val BRAND_NAME: String = "Coca Cola"
    private val BRAND_DESCRIPTION: String = "Coca Cola Company"

    /**
     * Cria uma instância do modelo de domínio [Brand].
     *
     * @param id O UUID da marca. O padrão é [BRAND_ID].
     * @param name O nome da marca. O padrão é [BRAND_NAME].
     * @param description A descrição da marca. O padrão é [BRAND_DESCRIPTION].
     * @return Uma instância de [Brand].
     */
    fun createABrand(
        id: UUID = BRAND_ID,
        name: String = BRAND_NAME,
        description: String = BRAND_DESCRIPTION
    ): Brand {
        return Brand(
            id = id,
            name = name,
            description = description
        )
    }

    /**
     * Cria uma instância do modelo de persistência [BrandEntity].
     *
     * @param id O UUID da entidade da marca. O padrão é [BRAND_ID].
     * @param name O nome da entidade da marca. O padrão é [BRAND_NAME].
     * @param description A descrição da entidade da marca. O padrão é [BRAND_DESCRIPTION].
     * @param active O status ativo da entidade da marca. O padrão é `true`.
     * @param createdAt O carimbo de data/hora de criação. O padrão é 7 dias atrás.
     * @param updatedAt O carimbo de data/hora da atualização. O padrão é agora.
     * @return Uma instância de [BrandEntity].
     */
    fun createABrandEntity(
        id: UUID = BRAND_ID,
        name: String = BRAND_NAME,
        description: String = BRAND_DESCRIPTION,
        active: Boolean = true,
        createdAt: LocalDateTime = LocalDateTime.now().minusDays(7),
        updatedAt: LocalDateTime = LocalDateTime.now()
    ): BrandEntity {
        return BrandEntity(
            id = id,
            name = name,
            description = description,
            active = active,
            createdAt = createdAt,
            updatedAt = updatedAt
        )
    }

    /**
     * Cria uma instância de DTO [CreateBrandRequest].
     *
     * @param name O nome para a solicitação de criação. O padrão é [BRAND_NAME].
     * @param description A descrição para a solicitação de criação. O padrão é [BRAND_DESCRIPTION].
     * @return Uma instância de [CreateBrandRequest].
     */
    fun createACreateBrandRequest(
        name: String = BRAND_NAME,
        description: String = BRAND_DESCRIPTION
    ): CreateBrandRequest {
        return CreateBrandRequest(
            name = name,
            description = description
        )
    }

    /**
     * Cria uma instância de DTO [UpdateBrandRequest].
     *
     * @param name O nome para a solicitação de atualização. O padrão é [BRAND_NAME].
     * @param description A descrição para a solicitação de atualização. O padrão é [BRAND_DESCRIPTION].
     * @param active O status ativo para a solicitação de atualização. O padrão é `true`.
     * @return Uma instância de [UpdateBrandRequest].
     */
    fun createAnUpdateBrandRequest(
        name: String = BRAND_NAME,
        description: String = BRAND_DESCRIPTION,
        active: Boolean = true
    ): UpdateBrandRequest {
        return UpdateBrandRequest(
            name = name,
            description = description,
            active = active
        )
    }

    /**
     * Cria uma instância de comando do serviço de aplicativo [CreateBrandCommand].
     *
     * @param name O nome para o comando de criação. O padrão é [BRAND_NAME].
     * @param description A descrição para o comando de criação. O padrão é [BRAND_DESCRIPTION].
     * @return Uma instância de [CreateBrandCommand].
     */
    fun createACreateCommandBrand(
        name: String = BRAND_NAME,
        description: String = BRAND_DESCRIPTION
    ): CreateBrandCommand {
        return CreateBrandCommand(
            name = name,
            description = description
        )
    }

    /**
     * Cria uma instância de comando do serviço de aplicativo [UpdateBrandCommand].
     *
     * @param id O UUID para o comando de atualização. O padrão é [BRAND_ID].
     * @param name O nome para o comando de atualização. O padrão é [BRAND_NAME].
     * @param description A descrição para o comando de atualização. O padrão é [BRAND_DESCRIPTION].
     * @param active O status ativo para o comando de atualização. O padrão é `true`.
     * @return Uma instância de [UpdateBrandCommand].
     */
    fun createAnUpdateCommandBrand(
        id: UUID = BRAND_ID,
        name: String = BRAND_NAME,
        description: String = BRAND_DESCRIPTION,
        active: Boolean = true
    ): UpdateBrandCommand {
        return UpdateBrandCommand(
            id = id,
            name = name,
            description = description,
            active = active
        )
    }

    /**
     * Cria um [Set] de instâncias de [Brand].
     *
     * @param quantity O número de instâncias a serem criadas. O padrão é 2.
     * @return Um [Set] de instâncias de [Brand].
     */
    fun createBrandList(quantity: Int = 2): Set<Brand> {
        return (1..quantity).map {
            val brandFakeRandom = "Brand of ${UUID.randomUUID()}"
            val descriptionOfBrandFakeRandom = "Description of $brandFakeRandom"
            createABrand(name = brandFakeRandom, description = descriptionOfBrandFakeRandom)
        }.toSet()
    }

    /**
     * Exibe os dados padrões da instancia
     * @return Uma [Map] com os valores
     */
    fun getAllProperties(): Map<String, Any> {
        return mapOf(
            "id" to BRAND_ID,
            "name" to BRAND_NAME,
            "description" to BRAND_DESCRIPTION
        )
    }
}