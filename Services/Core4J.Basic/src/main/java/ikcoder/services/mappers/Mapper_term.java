package ikcoder.services.mappers;

import ikcoder.entities.coredb_basic.DTI.DTI_term;
import ikcoder.entities.coredb_basic.DTO.DTO_term;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface Mapper_term {

    public void Insert_NewItem(DTI_term dti_term);

    public void Remove_Item(Integer id);

    public DTO_term Select_StartYear(String year);

    public DTO_term Select_EndYear(String end);

    public List<DTO_term> Select_InstCode(String instCode);

}
