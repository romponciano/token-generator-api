package com.rom.domain.repository;

import com.rom.domain.entity.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface TokenRepository extends MongoRepository<Token, String> {
}
