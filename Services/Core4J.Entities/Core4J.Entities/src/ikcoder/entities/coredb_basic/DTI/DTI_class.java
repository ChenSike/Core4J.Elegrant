package ikcoder.entities.coredb_basic.DTI;

public class DTI_class {

    String docid_inst;
    String name;
    Integer uid_owner;
    String current_term;

    public String getCurrent_term() {
        return current_term;
    }

    public void setCurrent_term(String current_term) {
        this.current_term = current_term;
    }

    public String getDocid_inst() {
        return docid_inst;
    }

    public void setDocid_inst(String docid_inst) {
        this.docid_inst = docid_inst;
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
}
