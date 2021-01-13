package ikcoder.services.mappers;


import ikcoder.entities.coredb_basic.DTI.DTI_users_mapinfo;

public interface Mapper_users_mapinfo {

    public boolean InsertMapinfo(DTI_users_mapinfo DTI_users_mapinfo);

    public boolean UpdateMapinfo_docid(DTI_users_mapinfo DTI_users_mapinfo);

    public boolean UpdateMapinfo_appid(DTI_users_mapinfo DTI_users_mapinfo);

    public String SelectMapinfo_docid(DTI_users_mapinfo DTI_users_mapinfo);

    public String SelectMapinfo_appid(DTI_users_mapinfo DTI_users_mapinfo);

    public boolean RemoveMapinfo(DTI_users_mapinfo DTI_users_mapinfo);

}
