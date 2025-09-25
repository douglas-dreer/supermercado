package br.com.supermercado.estoque.infrastructure.adapter.output.persistence.adapter

import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.domain.model.Brand
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.mapper.BrandEntityMapper
import br.com.supermercado.estoque.infrastructure.adapter.output.persistence.repository.BrandJpaRepository
import br.com.supermercado.estoque.infrastructure.common.annotation.Adapter
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

import java.util.UUID

@Adapter
class BrandRepositoryAdapter(
    private val repository: BrandJpaRepository,
    private val mapper: BrandEntityMapper
) : BrandRepositoryPort {

    override fun save(brand: Brand): Brand {
        val entity = mapper.toEntity(brand)
        val savedEntity = repository.save(entity)
        return mapper.toDomain(savedEntity)
    }

    override fun update(brand: Brand): Brand {
        val entity = mapper.toEntity(brand)
        val updatedEntity = repository.save(entity)
        return mapper.toDomain(updatedEntity)
    }

    override fun findById(id: UUID): Brand? {
        return repository.findById(id)
            .map { mapper.toDomain(it) }
            .orElse(null)
    }

    override fun findByName(name: String): Brand? {
        return repository.findByName(name)?.let { mapper.toDomain(it) }
    }

    override fun findAll(pageable: Pageable): Page<Brand> {
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
}