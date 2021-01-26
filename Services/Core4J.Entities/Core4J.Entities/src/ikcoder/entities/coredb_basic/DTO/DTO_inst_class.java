package ikcoder.entities.coredb_basic.DTO;


import ikcoder.entities.coredb_basic.DT.DT_inst_class;
import ikcoder.entities.coredb_basic.DTC.DTC_common;

import java.util.List;

public class DTO_inst_class extends DTC_common {

    String uid;

    List<DT_inst_class> lstClass;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public List<DT_inst_class> getLstClass() {
        return lstClass;
    }

    public void setLstClass(List<DT_inst_class> lstClass) {
        this.lstClass = lstClass;
    }
}
