package com.marvel.BackService.Model.Database.Repository;

import com.marvel.BackService.Model.Database.Tables.HeroCatalog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "herocatalog")
public interface HeroCatalogRepository extends JpaRepository<HeroCatalog, Long> {

}
