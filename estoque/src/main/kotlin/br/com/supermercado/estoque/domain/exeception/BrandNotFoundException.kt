package br.com.supermercado.estoque.domain.exeception

import java.util.UUID

class BrandNotFoundException(brandId: UUID) : DomainException("Brand not found with id: $brandId")