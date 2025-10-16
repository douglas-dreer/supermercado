package br.com.supermercado.estoque.application.port.output

import br.com.supermercado.estoque.domain.model.Product
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

interface ProductRepositoryPort {
    fun save(product: Product): Product
    fun findById(id: UUID): Product?
    fun findByBarcode(barcode: String): Product?
    fun findAll(pageable: Pageable): Page<Product>
    fun findByBrandId(brandId: UUID, pageable: Pageable): Page<Product>
    fun deleteById(id: UUID)
    fun existsById(id: UUID): Boolean
    fun existsByBarcode(barcode: String): Boolean
    fun countByBrandId(brandId: UUID): Long
    fun existsByName(productName: String): Boolean
}