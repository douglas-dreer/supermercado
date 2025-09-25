package br.com.supermercado.estoque.domain.exception

import java.util.UUID

class BrandNotFoundException(brandId: UUID) : DomainException("Brand not found with id: $brandId")