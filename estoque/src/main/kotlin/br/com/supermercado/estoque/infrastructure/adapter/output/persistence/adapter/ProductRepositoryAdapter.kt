package br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter

import br.com.supermercado.estoque.application.port.output.ProductRepositoryPort
import br.com.supermercado.estoque.domain.model.Product
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.mapper.ProductEntityMapper
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.repository.ProductJpaRepository
import br.com.supermercado.estoque.infrastructure.common.annotation.Adapter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

@Adapter
class ProductRepositoryAdapter(
    private val repository: ProductJpaRepository,
    private val mapper: ProductEntityMapper
) : ProductRepositoryPort {

    override fun save(product: Product): Product {
        val entity = mapper.toEntity(product)
        val savedEntity = repository.save(entity)
        return mapper.toDomain(savedEntity)
    }

    override fun findById(id: UUID): Product? {
        return repository.findById(id)
            .map { mapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByBarcode(barcode: String): Product? {
        return repository.findByBarcode(barcode)?.let { mapper.toDomain(it) }
    }

    override fun findAll(pageable: Pageable): Page<Product> {
        return repository.findAll(pageable)
            .map { mapper.toDomain(it) }
    }

    override fun findByBrandId(brandId: UUID, pageable: Pageable): Page<Product> {
        return repository.findByBrandId(brandId, pageable)
            .map { mapper.toDomain(it) }
    }

    override fun deleteById(id: UUID) {
        repository.deleteById(id)
    }

    override fun existsById(id: UUID): Boolean {
        return repository.existsById(id)
    }

    override fun existsByBarcode(barcode: String): Boolean {
        return repository.existsByBarcode(barcode)
    }

    override fun countByBrandId(brandId: UUID): Long {
        return repository.countByBrandId(brandId)
    }
}