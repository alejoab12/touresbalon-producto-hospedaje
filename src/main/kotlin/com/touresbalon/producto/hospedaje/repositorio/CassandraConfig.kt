package com.touresbalon.producto.hospedaje.repositorio

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories

@Configuration
@EnableCassandraRepositories(basePackages = ["com.touresbalon.producto.hospedaje.repositorio"])
class CassandraConfig : AbstractCassandraConfiguration() {

    private val KEY_SPACE_NAME = "touresbalon_producto_hospedaje"
    private val HOST_NAME = "127.0.0.1"
    private val PORT = 9042

    override fun getKeyspaceName(): String = KEY_SPACE_NAME

    @Bean
    override fun cluster(): CassandraClusterFactoryBean {
        val cluster = CassandraClusterFactoryBean()
        cluster.setContactPoints(HOST_NAME)
        cluster.setPort(PORT)
        return cluster
    }

    @Bean
    override fun cassandraMapping(): CassandraMappingContext {
        return CassandraMappingContext()
    }

}