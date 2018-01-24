package events.project.repositories;


import events.project.model.Address;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long>, JpaSpecificationExecutor<Address> {

    @Query(
            value = "select * from address where city=:city and number=:number and street=:street",
            nativeQuery = true)
    Address checkIfExist(@Param("city") String city, @Param("street") String street, @Param("number") int number);

}
