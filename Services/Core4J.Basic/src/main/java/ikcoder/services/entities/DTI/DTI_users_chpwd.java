package ikcoder.services.entities.DTI;

import ikcoder.services.entities.DT.DT_users;

public class DTI_users_chpwd extends DT_users {

    private String newPassword;

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

}
