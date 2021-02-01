package ikcoder.services.entities.DTO;

import ikcoder.services.entities.DTC.DTC_common;

public class DTO_common_message extends DTC_common {

    String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
