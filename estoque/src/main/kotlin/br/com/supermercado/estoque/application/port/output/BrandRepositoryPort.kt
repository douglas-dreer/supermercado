package br.com.supermercado.estoque.application.port.output

import br.com.supermercado.estoque.domain.model.Brand
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.UUID

interface BrandRepositoryPort {
    fun findAll(pageable: Pageable): Page<Brand>
    fun findById(id: UUID): Brand?
    fun findByName(name: String): Brand?
    fun save(brand: Brand): Brand
    fun update(brand: Brand): Brand
    fun deleteById(id: UUID)
    fun existsById(id: UUID): Boolean
    fun existsByName(name: String): Boolean
}