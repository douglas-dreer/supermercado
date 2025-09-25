package br.com.supermercado.estoque.domain.model

import java.time.LocalDateTime
import java.util.*

data class Brand(
    val id: UUID? = null,
    val name: String,
    val description: String? = null,
    val active: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now()
) {
    init {
        require(name.isNotBlank()) { "Brand name cannot be blank" }
        require(name.length <= 100) { "Brand name cannot exceed 100 characters" }
    }

    fun update(
        name: String? = null,
        description: String? = null,
        active: Boolean? = null
    ): Brand {
        return copy(
            name = name ?: this.name,
            description = description ?: this.description,
            active = active ?: this.active,
            updatedAt = LocalDateTime.now()
        )
    }
}