package br.com.supermercado.estoque.infrastructure.adapter.output.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import java.util.UUID

@Entity
@Table(name = "products")
data class ProductEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    val id: UUID? = null,

    @Column(nullable = false, length = 200)
    val name: String,

    @Column(length = 1000)
    val description: String? = null,

    @Column(nullable = false, unique = true, length = 50)
    val barcode: String,

    @Column(nullable = false, precision = 10, scale = 2)
    val price: BigDecimal,

    @Column(name = "stock_quantity", nullable = false)
    val stockQuantity: Int = 0,

    @Column(name = "min_stock_quantity", nullable = false)
    val minStockQuantity: Int = 0,

    @Column(name = "brand_id", nullable = false)
    val brandId: UUID,

    @Column(nullable = false, length = 100)
    val category: String,

    @Column(nullable = false)
    val active: Boolean = true,

    @Column(name = "created_at", nullable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at", nullable = false)
    val updatedAt: LocalDateTime = LocalDateTime.now()
)