package br.com.supermercado.estoque.infrastructure.adapter.output.persistence.repository

import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.entity.ProductEntity
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

import java.util.UUID

@Repository
interface ProductJpaRepository : JpaRepository<ProductEntity, UUID> {
    fun findByBarcode(barcode: String): ProductEntity?
    fun findByBrandId(brandId: UUID, pageable: Pageable): Page<ProductEntity>
    fun existsByBarcode(barcode: String): Boolean
    fun countByBrandId(brandId: UUID): Long
}