package br.com.supermercado.estoque.domain.exception

class ProductAlreadyExistsException(barcode: String) : DomainException("Product already exists with barcode: $barcode")
