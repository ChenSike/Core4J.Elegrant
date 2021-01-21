package ikcoder.services.docs.nodes;

import java.util.List;

public class DOC_node_class {

    String name;
    String classid;
    Integer uid_owner;
    String startyear;
    Boolean isgoing;
    String term;
    List<DOC_node_student> lstStudents;

    public List<DOC_node_student> getLstStudents() {
        return lstStudents;
    }

    public void setLstStudents(List<DOC_node_student> lstStudents) {
        this.lstStudents = lstStudents;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getClassid() {
        return classid;
    }

    public void setClassid(String classid) {
        this.classid = classid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUid_owner() {
        return uid_owner;
    }

    public void setUid_owner(Integer uid_owner) {
        this.uid_owner = uid_owner;
    }

    public String getStartyear() {
        return startyear;
    }

    public void setStartyear(String startyear) {
        this.startyear = startyear;
    }

    public Boolean getIsgoing() {
        return isgoing;
    }

    public void setIsgoing(Boolean isgoing) {
        this.isgoing = isgoing;
    }

}
