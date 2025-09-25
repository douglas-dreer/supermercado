package br.com.supermercado.estoque.infrastructure.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "app.database")
data class DatabaseConfig(
    var pool: PoolConfig = PoolConfig()
) {
    data class PoolConfig(
        var maxSize: Int = 10,
        var minIdle: Int = 2,
        var maxLifetime: Long = 300000
    )
}