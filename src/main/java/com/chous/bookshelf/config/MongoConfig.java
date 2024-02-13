package com.chous.bookshelf.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.chous.bookshelf")
public class MongoConfig extends AbstractMongoClientConfiguration {
    private final MappingMongoConverter mappingMongoConverter;

    @Value("${spring.data.mongodb.database}")
    private String database;

    // После создания класса MongoConfig возникает следующая ошибка:
    // "Relying upon circular references is discouraged and they are prohibited by default.
    // Update your application to remove the dependency cycle between beans. As a last resort,
    // it may be possible to break the cycle automatically by setting spring.main.allow-circular-references to true."
    // Я решила её добавлением аннотации @Lazy.
    @Lazy
    @Autowired
    public MongoConfig(MappingMongoConverter mappingMongoConverter) {
        this.mappingMongoConverter = mappingMongoConverter;
    }

    @Override
    protected String getDatabaseName() {
        return database;
    }

    @Bean
    public GridFsTemplate gridFsTemplate() {
        return new GridFsTemplate(mongoDbFactory(), mappingMongoConverter);
    }
}
