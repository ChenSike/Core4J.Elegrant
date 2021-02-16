package ikcoder.services.entities.DTO;

import ikcoder.services.docs.nodes.DOC_node_student;
import ikcoder.services.entities.DTC.DTC_common;

public class DTO_data_dashbord_student extends DTC_common {

    DOC_node_student doc_node_student=new DOC_node_student();

    public DOC_node_student getDoc_node_student() {
        return doc_node_student;
    }

    public void setDoc_node_student(DOC_node_student doc_node_student) {
        this.doc_node_student = doc_node_student;
    }
}
