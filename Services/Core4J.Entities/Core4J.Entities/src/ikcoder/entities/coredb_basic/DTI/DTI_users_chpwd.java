package ikcoder.entities.coredb_basic.DTI;

import ikcoder.entities.coredb_basic.DT.DT_users;

public class DTI_users_chpwd extends DT_users {

    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
