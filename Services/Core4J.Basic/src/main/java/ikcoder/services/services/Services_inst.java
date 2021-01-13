package ikcoder.services.services;

import ikcoder.entities.coredb_basic.DT.DT_inst;
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

}
