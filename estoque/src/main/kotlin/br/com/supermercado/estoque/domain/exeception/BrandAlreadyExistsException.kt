package br.com.supermercado.estoque.domain.exeception

class BrandAlreadyExistsException(name: String) : DomainException("Brand already exists with name: $name")
