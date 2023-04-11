package msa.customer.repository;

import msa.customer.DAO.Member;
import org.springframework.data.cassandra.repository.CassandraRepository;

import java.util.Optional;

public interface SpringDataCassandraMemberRepository extends CassandraRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
