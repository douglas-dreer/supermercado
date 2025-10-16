package br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter

import br.com.supermercado.estoque.application.port.output.CategoryRepositoryPort
import br.com.supermercado.estoque.domain.model.Category
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.mapper.CategoryEntityMapper
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.repository.CategoryJpaRepository
import br.com.supermercado.estoque.infrastructure.common.annotation.Adapter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import java.util.*

@Adapter
class CategoryRepositoryAdapter(
    private val repository: CategoryJpaRepository,
    private val mapper: CategoryEntityMapper
) : CategoryRepositoryPort {

    override fun save(category: Category): Category {
        val entity = mapper.toEntity(category)
        val savedEntity = repository.save(entity)
        return mapper.toDomain(savedEntity)
    }

    override fun update(category: Category): Category {
        val entity = mapper.toEntity(category)
        val updatedEntity = repository.save(entity)
        return mapper.toDomain(updatedEntity)
    }

    override fun findById(id: UUID): Category? {
        return repository.findById(id)
            .map { mapper.toDomain(it) }
            .orElse(null)
    }

    override fun findAll(): Set<Category> {
        return repository.findAll().map { mapper.toDomain(it) }.toSet()
    }

    override fun findAll(pageable: Pageable): Page<Category> {
        return repository.findAll(pageable).map { mapper.toDomain(it) }
    }


    override fun deleteById(id: UUID) {
        repository.deleteById(id)
    }

    override fun existsById(id: UUID): Boolean {
        return repository.existsById(id)
    }

    override fun existsByName(name: String): Boolean {
        return repository.existsByName(name)
    }

    override fun deleteAll() {
        return repository.deleteAll()
    }

    override fun saveAll(categories: Set<Category>): Set<Category> {
        val entities = categories.map { mapper.toEntity(it) }
        val dataSaved = repository
            .saveAll(entities).map { mapper.toDomain(it) }
        return dataSaved.toSet()
    }
}