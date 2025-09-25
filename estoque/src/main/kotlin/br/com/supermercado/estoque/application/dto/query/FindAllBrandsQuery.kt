package br.com.supermercado.estoque.application.dto.query

import org.springframework.data.domain.Pageable


data class FindAllBrandsQuery(val pageable: Pageable)