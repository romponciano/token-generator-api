package com.rom.domain.repository;

import com.rom.domain.entity.Model;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModelRepository extends MongoRepository<Model, String> {
    List<Model> findByUserId(String userId);
}
