package ikcoder.services.services;

import ikcoder.services.entities.DTO.DTO_users_basicinfo;
import ikcoder.services.mappers.Mapper_users_basicinfo;
import org.springframework.stereotype.Component;

@Component
public class Services_users_basicinfo {

    Mapper_users_basicinfo mapper_users_basicinfo;

    public Services_users_basicinfo(Mapper_users_basicinfo mapper_users_basicinfo) {
        this.mapper_users_basicinfo = mapper_users_basicinfo;
    }

    public DTO_users_basicinfo GetUserBasicInfo(Integer userid)
    {
        return mapper_users_basicinfo.Select_users_basicinfo(userid);
    }

    public String GetUserName(Integer userid)
    {
        return mapper_users_basicinfo.Select_users_basicinfo(userid).getName();
    }

}
