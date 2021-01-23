package com.mtjb.examples.test_profiles

import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Profile
import org.springframework.core.env.ConfigurableEnvironment
import org.springframework.core.env.PropertiesPropertySource
import org.springframework.test.context.ContextConfiguration
import org.testcontainers.containers.MSSQLServerContainer
import spock.lang.Shared
import spock.lang.Specification

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement

@Profile("mssql-test")
@ContextConfiguration(initializers = Initializer.class)
class SqlSpec extends Specification {

    @Shared
    static MSSQLServerContainer mssqlServerContainer

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        @Override
        void initialize(ConfigurableApplicationContext configurableApplicationContext) {

            mssqlServerContainer = new MSSQLServerContainer()
            mssqlServerContainer.start()

            ConfigurableEnvironment environment = configurableApplicationContext.getEnvironment()

            String db = environment.getProperty("test.db.name")
            String connectionString = "${mssqlServerContainer.getJdbcUrl()};integratedSecurity=false;username=${mssqlServerContainer.getUsername()};password=${mssqlServerContainer.getPassword()}"
            String jdbcUrl = connectionString + ";databaseName=${db}"

            Connection connection = DriverManager.getConnection(connectionString)
            PreparedStatement ps = connection.prepareStatement("IF(db_id(N'${db}') IS NULL) CREATE DATABASE ${db}")
            ps.execute()

            Properties props = new Properties()
            props.put("spring.datasource.driver-class-name", mssqlServerContainer.getDriverClassName())
            props.put("spring.datasource.url", jdbcUrl)

            environment.getPropertySources().addFirst(new PropertiesPropertySource("myTestDBProps", props))
            configurableApplicationContext.setEnvironment(environment);
        }
    }
}
