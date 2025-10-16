package br.com.supermercado.estoque.infrastructure.adapter.output.persistence.repository

import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.entity.BrandEntity
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.entity.CategoryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CategoryJpaRepository : JpaRepository<CategoryEntity, UUID> {
    fun findByName(name: String): BrandEntity?
    fun existsByName(name: String): Boolean
}