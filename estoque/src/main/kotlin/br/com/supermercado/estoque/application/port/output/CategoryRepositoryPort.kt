package br.com.supermercado.estoque.application.port.output

import br.com.supermercado.estoque.domain.model.Category
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

interface CategoryRepositoryPort {
    fun findAll(): Set<Category>
    fun findAll(pageable: Pageable): Page<Category>
    fun findById(id: UUID): Category?
    fun save(category: Category): Category
    fun update(category: Category): Category
    fun deleteById(id: UUID)
    fun existsById(id: UUID): Boolean
    fun existsByName(name: String): Boolean
    fun deleteAll()
    fun saveAll(category: Set<Category>): Set<Category>
}