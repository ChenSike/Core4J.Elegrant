package ikcoder.services.docservices;

import ikcoder.services.docs.nodes.DOC_node_topic;

import java.util.Date;
import java.util.List;

public class DocServices_topic {

    String docid_inst;
    Integer id_creator;
    String name_creator;
    Date lastModifyed;
    String name_modifyed;
    String subject;
    String grade;

    List<DOC_node_topic> topics_pool;

    public String getDocid_inst() {
        return docid_inst;
    }

    public void setDocid_inst(String docid_inst) {
        this.docid_inst = docid_inst;
    }

    public Integer getId_creator() {
        return id_creator;
    }

    public void setId_creator(Integer id_creator) {
        this.id_creator = id_creator;
    }

    public String getName_creator() {
        return name_creator;
    }

    public void setName_creator(String name_creator) {
        this.name_creator = name_creator;
    }

    public Date getLastModifyed() {
        return lastModifyed;
    }

    public void setLastModifyed(Date lastModifyed) {
        this.lastModifyed = lastModifyed;
    }

    public String getName_modifyed() {
        return name_modifyed;
    }

    public void setName_modifyed(String name_modifyed) {
        this.name_modifyed = name_modifyed;
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

    public List<DOC_node_topic> getTopics_pool() {
        return topics_pool;
    }

    public void setTopics_pool(List<DOC_node_topic> topics_pool) {
        this.topics_pool = topics_pool;
    }
}
