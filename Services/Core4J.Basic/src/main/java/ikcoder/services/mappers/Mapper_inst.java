package ikcoder.services.mappers;

import ikcoder.entities.coredb_basic.DT.DT_inst;
import ikcoder.entities.coredb_basic.DTI.DTI_inst;

public interface Mapper_inst {

    public void Insert_inst(DTI_inst dti_inst);

    public DT_inst Select_inst_code(String code);

    public DT_inst Select_inst_id(Integer id);

}
