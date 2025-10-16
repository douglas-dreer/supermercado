package br.com.supermercado.estoque.domain.exception

import java.util.*

class CategoryAlreadyExistException(categoryId: UUID) : DomainException("Category not found with id: $categoryId")