package ikcoder.services.entities.DTO;

import ikcoder.services.entities.DTC.DTC_common;

import java.util.ArrayList;
import java.util.List;

public class DTO_data_dashbord_studentstruct extends DTC_common {

    List<String> structAttributes=new ArrayList<>();

    public List<String> getStructAttributes() {
        return structAttributes;
    }

    public void setStructAttributes(List<String> structAttributes) {
        this.structAttributes = structAttributes;
    }
}
