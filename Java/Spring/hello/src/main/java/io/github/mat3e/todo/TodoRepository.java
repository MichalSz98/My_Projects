package io.github.mat3e.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface TodoRepository extends JpaRepository<Todo, Integer> {
    
}
