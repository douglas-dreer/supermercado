package br.com.supermercado.estoque.domain.exception

class BrandAlreadyExistsException(name: String) : DomainException("Brand already exists with name: $name")
