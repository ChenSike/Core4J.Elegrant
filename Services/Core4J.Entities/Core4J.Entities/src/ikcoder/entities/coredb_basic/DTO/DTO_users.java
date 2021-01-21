package ikcoder.entities.coredb_basic.DTO;

import ikcoder.entities.coredb_basic.DT.DT_users;

public class DTO_users extends DT_users {

    String code;
    String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
