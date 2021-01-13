package ikcoder.entities.coredb_basic.DT;

import ikcoder.entities.coredb_basic.DTC.DTC_common;

public class DT_users_mapinfo extends DTC_common {

    Integer id;
    Integer uid;
    String docid_basic;
    Integer inst_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getDocid_basic() {
        return docid_basic;
    }

    public void setDocid_basic(String docid_basic) {
        this.docid_basic = docid_basic;
    }

    public Integer getInst_id() {
        return inst_id;
    }

    public void setInst_id(Integer inst_id) {
        this.inst_id = inst_id;
    }
}
