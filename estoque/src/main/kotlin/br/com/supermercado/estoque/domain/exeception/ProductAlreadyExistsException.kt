package br.com.supermercado.estoque.domain.exeception

class ProductAlreadyExistsException(barcode: String) : DomainException("Product already exists with barcode: $barcode")
