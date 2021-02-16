package ikcoder.services.entities.DTI;

import java.util.Map;

public class DTI_inst_student_import_rmap {

    Map<String,String> RMap_name;
    Map<Integer,Integer> RMap_index;
    String classid;

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public Map<String, String> getRMap_name() {
        return RMap_name;
    }

    public void setRMap_name(Map<String, String> RMap_name) {
        this.RMap_name = RMap_name;
    }

    public Map<Integer, Integer> getRMap_index() {
        return RMap_index;
    }

    public void setRMap_index(Map<Integer, Integer> RMap_index) {
        this.RMap_index = RMap_index;
    }
}
