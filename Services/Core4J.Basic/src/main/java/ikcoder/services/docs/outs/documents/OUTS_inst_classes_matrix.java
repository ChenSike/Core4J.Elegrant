package ikcoder.services.docs.outs.documents;

import ikcoder.services.docs.outs.nodes.OUTS_node_inst_classes_class;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OUTS_inst_classes_matrix {

    String inst_name;
    String inst_code;
    String uid;
    String now;

    Map<String, List<OUTS_node_inst_classes_class>> classes_matrix=new HashMap<>();

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }

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

    public Map<String, List<OUTS_node_inst_classes_class>> getClasses_matrix() {
        return classes_matrix;
    }

    public void setClasses_matrix(Map<String, List<OUTS_node_inst_classes_class>> classes_matrix) {
        this.classes_matrix = classes_matrix;
    }
}
