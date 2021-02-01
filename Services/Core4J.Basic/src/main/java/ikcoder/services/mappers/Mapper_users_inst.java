package ikcoder.services.mappers;


import ikcoder.services.entities.DTO.DTO_users_inst;

import java.util.List;

public interface Mapper_users_inst {

    public List<DTO_users_inst> Select_userid(Integer userid);

}
