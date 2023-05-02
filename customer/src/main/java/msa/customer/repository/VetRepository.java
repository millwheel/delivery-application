package msa.customer.repository;

import java.util.UUID;

import msa.customer.DAO.Vet;
import org.springframework.data.repository.CrudRepository;

public interface VetRepository extends CrudRepository<Vet, UUID> {
    Vet findByFirstName(String username);
}
