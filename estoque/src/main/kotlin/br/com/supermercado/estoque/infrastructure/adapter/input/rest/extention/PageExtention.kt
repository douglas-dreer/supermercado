package br.com.supermercado.estoque.infrastructure.adapter.input.rest.extention

import br.com.supermercado.estoque.infrastructure.adapter.input.rest.dto.response.PageResponse
import org.springframework.data.domain.Page

object PageExtention {
    fun <T, R> Page<T>.toPageResponse(mapper: (T) -> R): PageResponse<R> {
        return PageResponse(
            content = this.content.map(mapper),
            page = this.number,
            size = this.size,
            totalElements = this.totalElements,
            totalPages = this.totalPages,
            first = this.isFirst,
            last = this.isLast
        )
    }
}