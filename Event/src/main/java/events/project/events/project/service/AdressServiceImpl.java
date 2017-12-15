package events.project.events.project.service;


import events.project.model.Point;
import events.project.repositories.AdressRepository;
import events.project.repositories.PointRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("adressService")
@Transactional
public class AdressServiceImpl implements  AdressService{


    private AdressRepository adressRepository;
    @Autowired
    public AdressServiceImpl(AdressRepository ar){
        this.adressRepository=ar;
    }


}
