package br.com.supermercado.estoque.infrastructure.common.annotation

import org.springframework.beans.factory.annotation.Qualifier

@Target(AnnotationTarget.CLASS, AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DeleteValidation

