package io.github.mat3e.lang;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LangRepository extends JpaRepository<Lang, Integer> {

}
