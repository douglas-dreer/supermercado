package br.com.supermercado.estoque.domain.exception

import java.util.*

class CategoryNotFoundException(categoryId: UUID) : DomainException("Category not found with id: $categoryId")