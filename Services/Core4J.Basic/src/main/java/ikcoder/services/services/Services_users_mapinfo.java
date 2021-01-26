package ikcoder.services.services;

import ikcoder.entities.coredb_basic.DT.DT_users_mapinfo;
import ikcoder.entities.coredb_basic.DTI.DTI_users_mapinfo;
import ikcoder.services.mappers.Mapper_users_mapinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class Services_users_mapinfo {

    Mapper_users_mapinfo mapperUsers_mapinfo;

    @Autowired
    public Services_users_mapinfo(Mapper_users_mapinfo mapperUsers_mapinfo) {
        this.mapperUsers_mapinfo = mapperUsers_mapinfo;
    }

    public boolean NewMapInfo(Integer uid)
    {
        try {
            DTI_users_mapinfo dti_users_mapinfo = new DTI_users_mapinfo();
            dti_users_mapinfo.setUid(uid);
            String docid_userpf = UUID.randomUUID().toString();
            dti_users_mapinfo.setDocid_userpf(docid_userpf);
            mapperUsers_mapinfo.InsertMapinfo(dti_users_mapinfo);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public String GetDocidUserPFByUID(Integer uid)
    {

        try {
            DT_users_mapinfo dt_users_mapinfo = mapperUsers_mapinfo.SelectMapinfoByUID(uid);
            return  dt_users_mapinfo.getDocid_userpf();
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }

    public String GetDocidUserPFbyID(Integer id)
    {
        try {
            DT_users_mapinfo dt_users_mapinfo = mapperUsers_mapinfo.SelectMapinfoByID(id);
            return  dt_users_mapinfo.getDocid_userpf();
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }

    public boolean RemoveUserMapinfo(Integer id)
    {
        try {
            mapperUsers_mapinfo.RemoveMapinfo(id);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

}
