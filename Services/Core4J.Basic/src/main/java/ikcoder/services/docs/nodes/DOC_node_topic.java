package ikcoder.services.docs.nodes;

import java.util.Date;

public class DOC_node_topic {

    String id;
    String type;
    String subject;
    String grade;
    Date recorded;
    String id_creator;
    String name_creator;
    String name_lastmodifyed;
    String lastmodifyed;
    String knowledgeid;

    DOC_node_topic_selectitem doc_node_topic_selectitem;
    DOC_node_topic_judgement doc_node_topic_judgement;
    DOC_node_topic_subjective doc_node_topic_subjective;
    DOC_node_topic_writen doc_node_topic_writen;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public Date getRecorded() {
        return recorded;
    }

    public void setRecorded(Date recorded) {
        this.recorded = recorded;
    }

    public String getId_creator() {
        return id_creator;
    }

    public void setId_creator(String id_creator) {
        this.id_creator = id_creator;
    }

    public String getName_creator() {
        return name_creator;
    }

    public void setName_creator(String name_creator) {
        this.name_creator = name_creator;
    }

    public String getName_lastmodifyed() {
        return name_lastmodifyed;
    }

    public void setName_lastmodifyed(String name_lastmodifyed) {
        this.name_lastmodifyed = name_lastmodifyed;
    }

    public String getLastmodifyed() {
        return lastmodifyed;
    }

    public void setLastmodifyed(String lastmodifyed) {
        this.lastmodifyed = lastmodifyed;
    }

    public String getKnowledgeid() {
        return knowledgeid;
    }

    public void setKnowledgeid(String knowledgeid) {
        this.knowledgeid = knowledgeid;
    }

    public DOC_node_topic_selectitem getDoc_node_topic_selectitem() {
        return doc_node_topic_selectitem;
    }

    public void setDoc_node_topic_selectitem(DOC_node_topic_selectitem doc_node_topic_selectitem) {
        this.doc_node_topic_selectitem = doc_node_topic_selectitem;
    }

    public DOC_node_topic_judgement getDoc_node_topic_judgement() {
        return doc_node_topic_judgement;
    }

    public void setDoc_node_topic_judgement(DOC_node_topic_judgement doc_node_topic_judgement) {
        this.doc_node_topic_judgement = doc_node_topic_judgement;
    }

    public DOC_node_topic_subjective getDoc_node_topic_subjective() {
        return doc_node_topic_subjective;
    }

    public void setDoc_node_topic_subjective(DOC_node_topic_subjective doc_node_topic_subjective) {
        this.doc_node_topic_subjective = doc_node_topic_subjective;
    }

    public DOC_node_topic_writen getDoc_node_topic_writen() {
        return doc_node_topic_writen;
    }

    public void setDoc_node_topic_writen(DOC_node_topic_writen doc_node_topic_writen) {
        this.doc_node_topic_writen = doc_node_topic_writen;
    }
}
