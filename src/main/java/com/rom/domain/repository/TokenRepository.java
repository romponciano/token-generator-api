package com.rom.domain.repository;

import com.rom.domain.entity.Token;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenRepository extends MongoRepository<Token, String> {

    List<Token> findByUser(String username);
}
