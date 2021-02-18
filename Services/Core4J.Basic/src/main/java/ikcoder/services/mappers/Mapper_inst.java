package ikcoder.services.mappers;

import ikcoder.services.entities.DT.DT_inst;
import ikcoder.services.entities.DTI.DTI_inst;

import java.util.List;

public interface Mapper_inst {

    public void Insert_inst(DTI_inst dti_inst);

    public DT_inst Select_inst_code(String code);

    public DT_inst Select_inst_id(Integer id);


}
