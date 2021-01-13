package ikcoder.services.mappers;

import ikcoder.entities.coredb_basic.DTO.DTO_users_inst;

import java.util.List;

public interface Mapper_users_inst {

    public List<DTO_users_inst> Select_userid(Integer userid);

}
