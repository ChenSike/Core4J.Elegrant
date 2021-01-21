package ikcoder.services.mappers;

import ikcoder.entities.coredb_basic.DTI.DTI_users;
import ikcoder.entities.coredb_basic.DTO.DTO_users;

import java.util.List;


public interface Mapper_users {

    public DTO_users Select_users_id(int id);

    public DTO_users Select_users_uid(String uid);

    public void Insert_users(DTI_users DTI_users);

    public void Update_users_pwd(int id,String pwd);

    public void Update_users_status(int id,String status);

    public void Delete_users_id(int id);

    public void Delete_users_uid(String username);

    public List<DTO_users> Select_users();

}
