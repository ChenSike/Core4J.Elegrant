package ikcoder.services.services;

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
            DTI_users_mapinfo DTI_users_mapinfo = new DTI_users_mapinfo();
            DTI_users_mapinfo.setUid(uid);
            String newDocId = UUID.randomUUID().toString();
            DTI_users_mapinfo.setDocid_basic(newDocId);
            String newAppId = UUID.randomUUID().toString();
            mapperUsers_mapinfo.InsertMapinfo(DTI_users_mapinfo);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

    public String GetDocId(Integer uid)
    {

        try {
            DTI_users_mapinfo DTI_users_mapinfo = new DTI_users_mapinfo();
            DTI_users_mapinfo.setUid(uid);
            return mapperUsers_mapinfo.SelectMapinfo_docid(DTI_users_mapinfo);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }

    public String GetAppId(Integer uid)
    {
        try {
            DTI_users_mapinfo DTI_users_mapinfo = new DTI_users_mapinfo();
            DTI_users_mapinfo.setUid(uid);
            return mapperUsers_mapinfo.SelectMapinfo_appid(DTI_users_mapinfo);
        }
        catch (Exception e)
        {
            return e.getMessage();
        }
    }

}
