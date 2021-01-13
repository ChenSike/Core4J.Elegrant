package ikcoder.entities.coredb_basic.DT;

import ikcoder.entities.coredb_basic.DTC.DTC_common;

public class DT_users extends DTC_common {

    int id;
    String uid;
    String pwd;
    String status;
    String usrtype;

    public String getUsrtype() {
        return usrtype;
    }

    public void setUsrtype(String usrtype) {
        this.usrtype = usrtype;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
