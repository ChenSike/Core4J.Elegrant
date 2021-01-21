package ikcoder.services.services;

import ikcoder.entities.coredb_basic.DTO.DTO_inst_mapinfo;
import ikcoder.services.mappers.Mapper_inst_mapinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.TreeMap;

@Component
public class Services_inst_mapinfo {

    Mapper_inst_mapinfo mapper_inst_mapinfo;

    @Autowired
    public Services_inst_mapinfo(Mapper_inst_mapinfo mapper_inst_mapinfo) {
        this.mapper_inst_mapinfo = mapper_inst_mapinfo;
    }

    public boolean NewItem(String inst_code,String docid_basic)
    {
        if(inst_code.equals(""))
            return false;
        else
        {

            mapper_inst_mapinfo.Insert_item(inst_code,docid_basic);
            return true;
        }
    }

    public DTO_inst_mapinfo SelectItemByCode(String inst_code)
    {
        DTO_inst_mapinfo result = mapper_inst_mapinfo.Select_item_code(inst_code);
        return  result;
    }

    public boolean IsExisted(String inst_code) {
        DTO_inst_mapinfo result = mapper_inst_mapinfo.Select_item_code(inst_code);
        if (result != null)
            return true;
        else
            return false;
    }

}
