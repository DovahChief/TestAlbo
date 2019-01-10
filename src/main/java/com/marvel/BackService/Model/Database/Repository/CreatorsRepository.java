package com.marvel.BackService.Model.Database.Repository;

import com.marvel.BackService.Model.Database.Tables.Creator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;


@RepositoryRestResource(collectionResourceRel = "creators")
public interface CreatorsRepository extends JpaRepository<Creator, Long> {
    @Override
    Optional<Creator> findById(Long aLong);
    Optional<Creator> findByCreatorName(String creator_name);
}
