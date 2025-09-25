package br.com.supermercado.estoque.infrastructure.common.constant

object ApiConstants {
    const val API_VERSION = "v1"
    const val BASE_PATH = "/api/$API_VERSION"

    const val DEFAULT_PAGE_SIZE = 10
    const val MAX_PAGE_SIZE = 100
    const val DEFAULT_SORT_FIELD = "createdAt"
    const val DEFAULT_SORT_DIRECTION = "DESC"
}