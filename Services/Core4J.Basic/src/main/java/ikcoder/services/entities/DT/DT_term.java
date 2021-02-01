package ikcoder.services.entities.DT;

public class DT_term {

    Integer id;
    String start_year;
    String start_month;
    String end_year;
    String end_month;
    String comment;
    String inst_code;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStart_year() {
        return start_year;
    }

    public void setStart_year(String start_year) {
        this.start_year = start_year;
    }

    public String getStart_month() {
        return start_month;
    }

    public void setStart_month(String start_month) {
        this.start_month = start_month;
    }

    public String getEnd_year() {
        return end_year;
    }

    public void setEnd_year(String end_year) {
        this.end_year = end_year;
    }

    public String getEnd_month() {
        return end_month;
    }

    public void setEnd_month(String end_month) {
        this.end_month = end_month;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getInst_code() {
        return inst_code;
    }

    public void setInst_code(String inst_code) {
        this.inst_code = inst_code;
    }
}
