package br.com.supermercado.estoque.infrastructure.common.util

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object DateUtil {
    private val DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    fun format(dateTime: LocalDateTime): String {
        return dateTime.format(DEFAULT_FORMATTER)
    }

    fun parse(dateString: String): LocalDateTime {
        return LocalDateTime.parse(dateString, DEFAULT_FORMATTER)
    }
}