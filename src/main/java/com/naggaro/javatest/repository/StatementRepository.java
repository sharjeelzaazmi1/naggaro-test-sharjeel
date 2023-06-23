package com.naggaro.javatest.repository;

import com.naggaro.javatest.entity.Statement;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author sharjeel.mehmood
 * @version 1.0
 * @since v1.0
 */
@Repository
public interface StatementRepository extends JpaRepository<Statement, Long> {
    List<Statement> findAll(Specification specification);
}
