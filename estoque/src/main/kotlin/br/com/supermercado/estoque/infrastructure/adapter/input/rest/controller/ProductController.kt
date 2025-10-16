package br.com.supermercado.estoque.infrastructure.adapter.input.rest.controller

import br.com.supermercado.estoque.application.port.input.product.CreateProductUseCase
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request.CreateProductRequest
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.response.PageResponse
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.response.ProductResponse
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.mapper.ProductMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Products", description = "Product management operations")
class ProductController(
    private val createProductUseCase: CreateProductUseCase,
    private val converter: ProductMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new product")
    fun createBrand(@Valid @RequestBody request: CreateProductRequest): ResponseEntity<ProductResponse> {
        val createCommand = converter.toCommander(request)
        val brand = createProductUseCase.execute(createCommand)
        return ResponseEntity.ok(converter.toResponse(brand))
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "List products")
    fun listProduct(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "name") sort: String,
        @RequestParam(defaultValue = "ASC") direction: String
    ): ResponseEntity<PageResponse<ProductResponse>> {
        TODO("Para implementar")
    }

}