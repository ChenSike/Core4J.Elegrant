package ikcoder.services.docs.documents;

import ikcoder.services.docs.nodes.DOC_node_knowlege;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("knowledges")
public class DOC_knowledges extends DOC_base {

    String id;
    String docid_inst;
    String subject;
    String grade;
    Map<String, DOC_node_knowlege> knowledgePool;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDocid_inst() {
        return docid_inst;
    }

    public void setDocid_inst(String docid_inst) {
        this.docid_inst = docid_inst;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Map<String, DOC_node_knowlege> getKnowledgePool() {
        return knowledgePool;
    }

    public void setKnowledgePool(Map<String, DOC_node_knowlege> knowledgePool) {
        this.knowledgePool = knowledgePool;
    }
}
