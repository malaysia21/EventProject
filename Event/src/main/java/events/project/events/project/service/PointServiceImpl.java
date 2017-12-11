package events.project.events.project.service;


import events.project.model.Event;
import events.project.model.EventType;
import events.project.model.Point;
import events.project.repositories.EventRepository;
import events.project.repositories.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

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
