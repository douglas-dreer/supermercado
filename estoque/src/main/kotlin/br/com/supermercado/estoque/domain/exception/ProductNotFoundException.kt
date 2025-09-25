package br.com.supermercado.estoque.domain.exception

import java.util.UUID

class ProductNotFoundException : DomainException {
    constructor(productId: UUID) : super("Product not found with id: $productId")
    constructor(barcode: String) : super("Product not found with barcode: $barcode")
}