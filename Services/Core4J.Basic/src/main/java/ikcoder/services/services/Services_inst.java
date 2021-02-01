package ikcoder.services.services;

import ikcoder.services.entities.DT.DT_inst;
import ikcoder.services.entities.DTI.DTI_inst;
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

    public Services_inst() {
    }

    public String GetFirstCode(Integer id) {
        DT_inst dt_inst = mapper_inst.Select_inst_id(id);
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

    public boolean IsExisted(String inst_code) {
        DT_inst dt_inst = mapper_inst.Select_inst_code(inst_code);
        if (dt_inst != null)
            return true;
        else
            return false;
    }

    public DT_inst SelectInst(String inst_code)
    {
        DT_inst dt_inst = mapper_inst.Select_inst_code(inst_code);
        return dt_inst;
    }

}
