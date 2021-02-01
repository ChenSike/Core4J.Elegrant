package ikcoder.services.mappers;


import ikcoder.services.entities.DT.DT_users_mapinfo;
import ikcoder.services.entities.DTI.DTI_users_mapinfo;

public interface Mapper_users_mapinfo {

    public boolean InsertMapinfo(DTI_users_mapinfo DTI_users_mapinfo);

    public DT_users_mapinfo SelectMapinfoByUID(Integer uid);

    public DT_users_mapinfo SelectMapinfoByID(Integer id);

    public boolean RemoveMapinfo(Integer id);

}
