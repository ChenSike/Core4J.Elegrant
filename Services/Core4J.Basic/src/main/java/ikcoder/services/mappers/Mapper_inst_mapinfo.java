package ikcoder.services.mappers;

import ikcoder.services.entities.DTO.DTO_inst_mapinfo;

public interface Mapper_inst_mapinfo {

    public void Insert_item(String inst_code,String docid_basic);

    public DTO_inst_mapinfo Select_item_code(String inst_code);

    public DTO_inst_mapinfo Select_item_id(Integer id);

}
