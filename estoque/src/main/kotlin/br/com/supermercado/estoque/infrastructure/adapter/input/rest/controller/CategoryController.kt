package br.com.supermercado.estoque.infrastructure.adapter.input.rest.controller

import br.com.supermercado.estoque.application.port.input.category.CreateCategoryUseCase
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request.CreateCategoryRequest
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.response.CategoryResponse
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.mapper.CategoryMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Category", description = "Brand management operations")
class CategoryController(
    private val createCategoryUseCase: CreateCategoryUseCase,
    private val converter: CategoryMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new category")
    fun createBrand(@Valid @RequestBody request: CreateCategoryRequest): ResponseEntity<CategoryResponse> {
        val createCommand = converter.toCommander(request)
        val brand = createCategoryUseCase.execute(createCommand)
        return ResponseEntity.ok(converter.toResponse(brand))
    }

}