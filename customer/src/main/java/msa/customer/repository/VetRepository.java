package msa.customer.repository;

import msa.customer.DAO.Vet;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface VetRepository extends CrudRepository<Vet, UUID> {
    Vet findByFirstName(String username);
}
