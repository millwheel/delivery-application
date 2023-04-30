package msa.customer.repository;

import msa.customer.DAO.Yet;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface YetRepository extends CrudRepository<Yet, UUID> {
    Yet findByFirstName(String username);
}
