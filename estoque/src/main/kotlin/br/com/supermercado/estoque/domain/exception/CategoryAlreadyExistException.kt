package br.com.supermercado.estoque.domain.exception

class CategoryAlreadyExistException(categoryName: String) :
    DomainException("Brand already exists with name: $categoryName")