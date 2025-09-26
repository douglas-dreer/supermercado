package br.com.supermercado.estoque.infrastructure.adapter.input.rest.controller

import br.com.supermercado.estoque.application.dto.query.FindAllBrandsQuery
import br.com.supermercado.estoque.application.dto.query.FindBrandByIdQuery
import br.com.supermercado.estoque.application.port.input.brand.*
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request.CreateBrandRequest
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.request.UpdateBrandRequest
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.response.BrandResponse
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.response.PageResponse
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.extention.PageExtension.toPageResponse
import br.com.supermercado.estoque.infrastructure.adapter.input.rest.mapper.BrandMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/brands")
@Tag(name = "Brands", description = "Brand management operations")
class BrandController(
    private val findBrandByIdUseCase: FindBrandByIdUseCase,
    private val findAllBrandsUseCase: FindAllBrandsUseCase,
    private val createBrandUseCase: CreateBrandUseCase,
    private val updateBrandUseCase: UpdateBrandUseCase,
    private val deleteBrandUseCase: DeleteBrandUseCase,
    private val converter: BrandMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a new brand")
    fun createBrand(@Valid @RequestBody request: CreateBrandRequest): ResponseEntity<BrandResponse> {
        val createCommand = converter.toCommander(request)
        val brand = createBrandUseCase.execute(createCommand)
        return ResponseEntity.ok(converter.toResponse(brand))
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get brand by ID")
    fun getBrandById(@PathVariable id: UUID): ResponseEntity<BrandResponse> {
        val query = FindBrandByIdQuery(id)
        val brand = findBrandByIdUseCase.execute(query)?: return ResponseEntity.ok().build()
        return ResponseEntity.ok(converter.toResponse(brand))
    }

    @GetMapping
    @Operation(summary = "Get all brands with pagination")
    fun getAllBrands(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "name") sort: String,
        @RequestParam(defaultValue = "ASC") direction: String
    ): ResponseEntity<PageResponse<BrandResponse>> {
        val sortDirection = Sort.Direction.fromString(direction)
        val pageable = PageRequest.of(page, size, Sort.by(sortDirection, sort))
        val query = FindAllBrandsQuery(pageable)

        val brandPage = findAllBrandsUseCase.execute(query)
        return ResponseEntity.ok(brandPage.toPageResponse { converter.toResponse(it) })
    }


    @PutMapping("/{id}")
    @Operation(summary = "Update brand")
    fun updateBrand(
        @PathVariable id: UUID,
        @Valid @RequestBody request: UpdateBrandRequest
    ): ResponseEntity<BrandResponse> {
       val updateCommand = converter.toCommander(id, request)

        val brandeUpdated = updateBrandUseCase.execute(updateCommand)
        return ResponseEntity.ok(converter.toResponse(brandeUpdated))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete brand")
    fun deleteBrand(@PathVariable id: UUID) {
        deleteBrandUseCase.execute(id)
    }
}