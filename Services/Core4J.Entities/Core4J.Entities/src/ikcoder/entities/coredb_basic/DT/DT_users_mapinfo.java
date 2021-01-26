package ikcoder.entities.coredb_basic.DT;

import ikcoder.entities.coredb_basic.DTC.DTC_common;

public class DT_users_mapinfo extends DTC_common {

    Integer id;
    Integer uid;
    Integer inst_id;
    String docid_userpf;

    public String getDocid_userpf() {
        return docid_userpf;
    }

    public void setDocid_userpf(String docid_userpf) {
        this.docid_userpf = docid_userpf;
    }

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

    public Integer getInst_id() {
        return inst_id;
    }

    public void setInst_id(Integer inst_id) {
        this.inst_id = inst_id;
    }
}
