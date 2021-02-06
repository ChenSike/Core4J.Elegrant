package ikcoder.services.services;

import ikcoder.services.entities.DTO.DTO_users_inst;
import ikcoder.services.mappers.Mapper_messages;
import ikcoder.services.mappers.Mapper_users_inst;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Services_users_inst {

    Mapper_users_inst _mapper_users_inst;
    Mapper_messages _mapper_messages;

    @Autowired
    public Services_users_inst(Mapper_users_inst mapper_users_inst,Mapper_messages mapper_messages)
    {
        this._mapper_users_inst=mapper_users_inst;
        this._mapper_messages=mapper_messages;
    }

    public DTO_users_inst getUsersInstMap(Integer userid)
    {
        DTO_users_inst result_users_inst=_mapper_users_inst.Select_userid(userid);
        return result_users_inst;
    }


}
