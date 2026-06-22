package br.com.stockFlow.repository;

import br.com.stockFlow.Model.Movimentation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface MovimentationRepository extends JpaRepository<Movimentation, UUID> {
}
