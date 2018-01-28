package events.project.repositories;


import events.project.modelEntity.Point;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
/**
 * Repozytorium dla encji Point
 * @version 1.1
 */
@Repository
public interface PointRepository extends CrudRepository<Point, Long>, JpaSpecificationExecutor<Point> {

    @Query(
            value = "select * from point where ABS(lon-:lon)<=0.000001 and ABS(lat-:lat)<=0.000001",
            nativeQuery = true)
    Point checkIfExist(@Param("lon") Float lon, @Param("lat") Float lat);
}
