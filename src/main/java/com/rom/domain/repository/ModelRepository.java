package com.rom.domain.repository;

import com.rom.domain.entity.Model;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface ModelRepository extends MongoRepository<Model, String> {
}
