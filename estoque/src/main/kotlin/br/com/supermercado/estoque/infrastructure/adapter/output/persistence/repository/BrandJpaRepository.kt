package br.com.supermercado.estoque.infrastructure.adapter.output.persistence.repository

import br.com.supermercado.estoque.domain.model.Brand
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.entity.BrandEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface BrandJpaRepository : JpaRepository<BrandEntity, UUID> {
    fun findByName(name: String): BrandEntity?
    fun existsByName(name: String): Boolean
}