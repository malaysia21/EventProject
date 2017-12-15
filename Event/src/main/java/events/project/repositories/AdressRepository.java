package events.project.repositories;


import events.project.model.Adress;
import events.project.model.Point;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends CrudRepository<Adress, Long>, JpaSpecificationExecutor<Adress> {

}
