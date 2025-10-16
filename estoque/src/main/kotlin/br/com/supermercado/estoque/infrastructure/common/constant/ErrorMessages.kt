package br.com.supermercado.estoque.infrastructure.common.constant

object ErrorMessages {
    const val BRAND_NOT_FOUND = "Brand not found"
    const val BRAND_ALREADY_EXISTS = "Brand already exists"
    const val BRAND_REQUIRED_ID = "Brand ID is required for update operation"

    const val PRODUCT_NOT_FOUND = "Product not found"
    const val PRODUCT_ALREADY_EXISTS = "Product already exists"
    const val PRODUCT_REQUIRED_ID = "Product ID is required for update operation"

    const val CATEGORY_NOT_FOUND = "Category not found"
    const val CATEGORY_ALREADY_EXISTS = "Category already exists"
    const val CATEGORY_REQUIRED_ID = "Category ID is required for update operation"

    const val VALIDATION_ERROR = "Validation error"
    const val INTERNAL_SERVER_ERROR = "Internal server error"
}