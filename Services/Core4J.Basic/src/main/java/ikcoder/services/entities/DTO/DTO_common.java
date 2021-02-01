package ikcoder.services.entities.DTO;

import ikcoder.services.entities.DTC.DTC_common;

public class DTO_common {

    Boolean IsExceptioned;
    String Code;
    String ReturnType;
    DTC_common Content;

    public Boolean getExceptioned() {
        return IsExceptioned;
    }

    public void setExceptioned(Boolean exceptioned) {
        IsExceptioned = exceptioned;
    }

    public String getCode() {
        return Code;
    }

    public void setCode(String code) {
        Code = code;
    }

    public String getReturnType() {
        return ReturnType;
    }

    public void setReturnType(String returnType) {
        ReturnType = returnType;
    }

    public DTC_common getContent() {
        return Content;
    }

    public void setContent(DTC_common content) {
        Content = content;
        this.setReturnType(content.getClass().getTypeName());
    }
}
