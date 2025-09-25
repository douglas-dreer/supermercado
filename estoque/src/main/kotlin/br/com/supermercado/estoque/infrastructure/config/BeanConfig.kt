package br.com.supermercado.estoque.infrastructure.config

import br.com.supermercado.estoque.application.dto.mapper.BrandCommandMapper
import br.com.supermercado.estoque.application.port.input.brand.CreateBrandUseCase
import br.com.supermercado.estoque.application.port.input.brand.DeleteBrandUseCase
import br.com.supermercado.estoque.application.port.input.brand.FindAllBrandsUseCase
import br.com.supermercado.estoque.application.port.input.brand.FindBrandByIdUseCase
import br.com.supermercado.estoque.application.port.input.brand.UpdateBrandUseCase
import br.com.supermercado.estoque.application.port.input.brand.validate.CreateBrandValidate
import br.com.supermercado.estoque.application.port.input.brand.validate.DeleteBrandValidate
import br.com.supermercado.estoque.application.port.input.brand.validate.UpdateBrandValidate
import br.com.supermercado.estoque.application.port.output.BrandRepositoryPort
import br.com.supermercado.estoque.application.usecase.brand.CreateBrandUseCaseImpl
import br.com.supermercado.estoque.application.usecase.brand.DeleteBrandUseCaseImpl
import br.com.supermercado.estoque.application.usecase.brand.FindAllBrandsUseCaseImpl
import br.com.supermercado.estoque.application.usecase.brand.FindBrandByIdUseCaseImpl
import br.com.supermercado.estoque.application.usecase.brand.UpdateBrandUseCaseImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {

     @Bean
    fun createBrandUseCase(brandRepository: BrandRepositoryPort, validate: CreateBrandValidate, converter: BrandCommandMapper): CreateBrandUseCase {
        return CreateBrandUseCaseImpl(brandRepository, converter,validate )
    }

    @Bean
    fun findAllBrandsUseCase(brandRepository: BrandRepositoryPort): FindAllBrandsUseCase {
        return FindAllBrandsUseCaseImpl(brandRepository)
    }

    @Bean
    fun findBrandByIdUseCase(brandRepository: BrandRepositoryPort): FindBrandByIdUseCase {
        return FindBrandByIdUseCaseImpl(brandRepository)
    }

    @Bean
    fun updateBrandUseCase(brandRepository: BrandRepositoryPort, converter: BrandCommandMapper, validate: UpdateBrandValidate): UpdateBrandUseCase {
        return UpdateBrandUseCaseImpl(brandRepository, converter, validate)
    }



    @Bean
    fun deleteBrandUseCase(brandRepository: BrandRepositoryPort, validate: DeleteBrandValidate): DeleteBrandUseCase {
        return DeleteBrandUseCaseImpl(brandRepository, validate)
    }


}