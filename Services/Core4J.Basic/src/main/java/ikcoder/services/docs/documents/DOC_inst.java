package ikcoder.services.docs.documents;

import ikcoder.services.docs.nodes.DOC_node_class;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document("inst")
public class DOC_inst extends DOC_base {

    String docid_inst;
    String inst_code;
    List<DOC_node_class> lstClass;

    public String getDocid_inst() {
        return docid_inst;
    }

    public void setDocid_inst(String docid_inst) {
        this.docid_inst = docid_inst;
    }

    public DOC_inst() {
        lstClass = new ArrayList<>();
    }

    public String getInst_code() {
        return inst_code;
    }

    public void setInst_code(String inst_code) {
        this.inst_code = inst_code;
    }

    public List<DOC_node_class> getLstClass() {
        return lstClass;
    }

    public void setLstClass(List<DOC_node_class> lstClass) {
        this.lstClass = lstClass;
    }

    public boolean isClassExisted(String name,String startYear)
    {
        boolean isFound = false;
        for(DOC_node_class doc_node_class : lstClass)
        {
            if(doc_node_class.getName().equals(name)&&doc_node_class.getStartyear().equals(startYear))
            {
                isFound=true;
                break;
            }
        }
        return isFound;
    }

    public DOC_node_class getClass(String uid_class)
    {
        boolean isFound = false;
        for(DOC_node_class doc_node_class : lstClass)
        {
            if(doc_node_class.getClassid().equals(uid_class))
            {
                return doc_node_class;
            }
        }
        return null;
    }

}
