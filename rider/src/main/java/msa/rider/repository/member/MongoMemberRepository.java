package msa.rider.repository.member;

import msa.rider.dto.rider.RiderAddressRequestDto;
import msa.rider.dto.rider.RiderRequestDto;
import msa.rider.dto.rider.RiderResponseDto;
import msa.rider.entity.member.Rider;
import msa.rider.exception.RiderNonexistentException;
import org.springframework.context.annotation.Primary;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Primary
@Repository
public class MongoMemberRepository implements MemberRepository {
    private final SpringDataMongoMemberRepository repository;

    public MongoMemberRepository(SpringDataMongoMemberRepository repository) {
        this.repository = repository;
    }

    @Override
    public String create(Rider rider) {
        Rider save = repository.save(rider);
        return save.getRiderId();
    }

    @Override
    public Optional<Rider> findById(String id) {
        return repository.findById(id);
    }

    @Override
    public void update(String riderId, RiderRequestDto data) {
        repository.findById(riderId).ifPresent(rider -> {
            if (data.getName() != null) rider.setName(data.getName());
            if (data.getPhoneNumber() != null) rider.setPhoneNumber(data.getPhoneNumber());
            repository.save(rider);
        });
    }

    @Override
    public void update(String riderId, RiderRequestDto data, Point location) {
        repository.findById(riderId).ifPresent(rider -> {
            if (data.getName() != null) rider.setName(data.getName());
            if (data.getPhoneNumber() != null) rider.setPhoneNumber(data.getPhoneNumber());
            rider.setAddress(data.getAddress());
            rider.setLocation(location);
            repository.save(rider);
        });
    }

    @Override
    public void updateAddress(String riderId, String address, Point location) {
        repository.findById(riderId).ifPresent(rider -> {
            rider.setAddress(address);
            rider.setLocation(location);
            repository.save(rider);
        });
    }

    @Override
    public void deleteById(String riderId) {
        repository.deleteById(riderId);
    }
}
