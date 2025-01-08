package pe.com.bibliAI.BibliAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.bibliAI.BibliAI.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}
