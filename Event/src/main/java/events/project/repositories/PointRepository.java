package events.project.repositories;


import events.project.model.Point;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends CrudRepository<Point, Long>, JpaSpecificationExecutor<Point> {

    @Query(
            value = "select * from point where dl-:dl<=0.000001 and sz-:sz<=0.000001",
            nativeQuery = true)
    Point checkIfExist(  @Param("dl") Float dl, @Param("sz") Float sz);
}
