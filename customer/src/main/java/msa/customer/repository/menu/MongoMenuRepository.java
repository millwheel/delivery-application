package msa.customer.repository.menu;

import msa.customer.DAO.Menu;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public class MongoMenuRepository implements MenuRepository{
    private final SpringDataMongoMenuRepository repository;

    public MongoMenuRepository(SpringDataMongoMenuRepository repository) {
        this.repository = repository;
    }

    @Override
    public void make(Menu menu) {
        repository.save(menu);
    }

    @Override
    public Optional<Menu> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void setName(String id, String name) {
        repository.findById(id).ifPresent(member -> {
            member.setName(name);
            repository.save(member);
        });
    }

    @Override
    public void setPrice(String id, int price) {
        repository.findById(id).ifPresent(member -> {
            member.setPrice(price);
            repository.save(member);
        });
    }

    @Override
    public void setDescription(String id, String description) {
        repository.findById(id).ifPresent(member ->{
            member.setDescription(description);
            repository.save(member);
        });
    }
}
