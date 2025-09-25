package br.com.supermercado.estoque.infrastructure.common.util

object ValidationUtil {
    fun isValidEmail(email: String?): Boolean {
        if (email.isNullOrBlank()) return false
        return email.matches(Regex("^[A-Za-z0-9+_.-]+@(.+)$"))
    }

    fun isValidBarcode(barcode: String?): Boolean {
        if (barcode.isNullOrBlank()) return false
        return barcode.matches(Regex("^[0-9]{8,14}$"))
    }

    fun isPositive(value: Number?): Boolean {
        return value != null && value.toDouble() > 0
    }
}