package ikcoder.services.services;

import ikcoder.entities.coredb_basic.DT.DT_inst;
import ikcoder.entities.coredb_basic.DTI.DTI_inst;
import ikcoder.services.mappers.Mapper_inst;
import ikcoder.services.mappers.Mapper_messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Services_inst {
    Mapper_inst mapper_inst;
    Mapper_messages mapper_messages;

    @Autowired
    public Services_inst(Mapper_inst mapper_inst, Mapper_messages mapper_messages) {
        this.mapper_inst = mapper_inst;
        this.mapper_messages = mapper_messages;
    }

    public String GetFirstCode(Integer id) {
        DT_inst dt_inst = mapper_inst.select_inst_id(id);
        return dt_inst.getCode();
    }

    public boolean NewItem(String instName,String instAddress,String type,String city,String code)
    {
        try {
            DTI_inst dti_inst = new DTI_inst();
            dti_inst.setName(instName);
            dti_inst.setAddress(instAddress);
            dti_inst.setType(type);
            dti_inst.setCity(city);
            dti_inst.setCode(code);
            mapper_inst.Insert_inst(dti_inst);
            return true;
        }
        catch (Exception err)
        {
            return false;
        }
    }

}
