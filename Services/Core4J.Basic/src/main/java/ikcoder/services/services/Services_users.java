package ikcoder.services.services;
import ikcoder.entities.coredb_basic.DTI.DTI_users;
import ikcoder.entities.coredb_basic.DTI.DTI_users_chpwd;
import ikcoder.entities.coredb_basic.DTO.DTO_users;
import ikcoder.entities.coredb_basic.DTO.DTO_users_id;
import ikcoder.services.mappers.Mapper_messages;
import ikcoder.services.mappers.Mapper_users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Services_users {

    private Mapper_messages _mappers_message;
    private Mapper_users _mapper_users;

    @Autowired
    public Services_users(Mapper_messages mapper_messages, Mapper_users mapper_users) {
        this._mapper_users = mapper_users;
        this._mappers_message = mapper_messages;
    }

    public boolean NewUser(DTI_users DTI_users)
    {
        if(DTI_users==null)
            return false;
        else
        {
            DTO_users DTO_users = _mapper_users.Select_users_uid(DTI_users.getUid());
            if(DTO_users==null) {
                _mapper_users.Insert_users(DTI_users);
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public DTO_users_id SelectUserId(DTI_users DTI_users)
    {
        DTO_users_id DTO_users_id = new DTO_users_id();
        DTO_users_id.setId(_mapper_users.Select_users_id(DTI_users.getId()).getId());
        return DTO_users_id;
    }

    public boolean ChangePwd(DTI_users_chpwd dti_users_chpwd)
    {
        if(dti_users_chpwd==null)
            return false;
        else
        {
            DTO_users DTO_users = _mapper_users.Select_users_uid(dti_users_chpwd.getUid());
            if(DTO_users!=null) {
                _mapper_users.Update_users_pwd(DTO_users.getId(),dti_users_chpwd.getNewPassword());
                return true;
            }
            else
            {
                return false;
            }
        }
    }

    public DTO_users GetUser(DTI_users DT_users) {
        DTO_users DTO_users = _mapper_users.Select_users_uid(DT_users.getUid());
        return DTO_users;
    }

    public int VerifyUser(DTI_users DT_users)
    {
        if(DT_users==null)
            return 0;
        else
        {
            DTO_users DTO_users = _mapper_users.Select_users_uid(DT_users.getUid());
            if(DTO_users!=null) {
                if (DT_users.getPwd().equals(DTO_users.getPwd()))
                    return 1;
                else
                    return 2;
            }
            else
            {
                return 3;
            }
        }
    }

    public List<DTO_users> GetUsers()
    {
        List<DTO_users> result = _mapper_users.Select_users();
        return result;
    }

}
