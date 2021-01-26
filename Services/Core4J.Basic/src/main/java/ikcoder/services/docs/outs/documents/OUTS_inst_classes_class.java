package ikcoder.services.docs.outs.documents;

import ikcoder.entities.coredb_basic.DTC.DTC_common;
import ikcoder.services.docs.nodes.DOC_node_student;
import ikcoder.services.docs.outs.nodes.OUTS_node_inst_classes_class;

import java.util.List;

public class OUTS_inst_classes_class extends DTC_common {

    String inst_name;
    String inst_code;
    String uid;
    String now;

    OUTS_node_inst_classes_class outs_node_inst_classes_class;
    List<DOC_node_student> lstStudents;

    public String getInst_name() {
        return inst_name;
    }

    public void setInst_name(String inst_name) {
        this.inst_name = inst_name;
    }

    public String getInst_code() {
        return inst_code;
    }

    public void setInst_code(String inst_code) {
        this.inst_code = inst_code;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

    public OUTS_node_inst_classes_class getOuts_node_inst_classes_class() {
        return outs_node_inst_classes_class;
    }

    public void setOuts_node_inst_classes_class(OUTS_node_inst_classes_class outs_node_inst_classes_class) {
        this.outs_node_inst_classes_class = outs_node_inst_classes_class;
    }

    public List<DOC_node_student> getLstStudents() {
        return lstStudents;
    }

    public void setLstStudents(List<DOC_node_student> lstStudents) {
        this.lstStudents = lstStudents;
    }
}
