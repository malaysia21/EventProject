package events.project.service;


import events.project.model.Point;
import events.project.repositories.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("pointService")
@Transactional
public class PointServiceImpl implements  PointService{


    private PointRepository pointRepository;
    @Autowired
    public PointServiceImpl(PointRepository pr){
        this.pointRepository=pr;
    }

    @Override
    public void savePoint(Point point) {
        {pointRepository.save(point);
        }
    }
}
