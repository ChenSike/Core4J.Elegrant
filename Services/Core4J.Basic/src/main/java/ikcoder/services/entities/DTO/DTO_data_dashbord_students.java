package ikcoder.services.entities.DTO;

import ikcoder.services.docs.nodes.DOC_node_student;
import ikcoder.services.docs.outs.nodes.OUTS_node_inst_classes_class;
import ikcoder.services.entities.DTC.DTC_common;

import java.util.List;

public class DTO_data_dashbord_students extends DTC_common {

    OUTS_node_inst_classes_class outs_node_inst_classes_class;
    List<DOC_node_student> lstStudents;

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
