package events.project.service;


import events.project.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("adressService")
@Transactional
public class AddressServiceImpl implements AddressService {


    private AddressRepository addressRepository;
    @Autowired
    public AddressServiceImpl(AddressRepository ar){
        this.addressRepository =ar;
    }


   

}
