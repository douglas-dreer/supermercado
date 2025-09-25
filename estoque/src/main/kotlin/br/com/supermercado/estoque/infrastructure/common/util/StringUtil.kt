package br.com.supermercado.estoque.infrastructure.common.util

object StringUtil {
    fun isBlankOrEmpty(value: String?): Boolean {
        return value.isNullOrBlank()
    }

    fun normalizeString(value: String?): String? {
        return value?.trim()?.takeIf { it.isNotEmpty() }
    }

    fun toSlug(value: String): String {
        return value.lowercase()
            .replace(Regex("[^a-z0-9\\s]"), "")
            .replace(Regex("\\s+"), "-")
            .trim('-')
    }
}