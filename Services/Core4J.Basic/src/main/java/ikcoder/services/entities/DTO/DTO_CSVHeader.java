package ikcoder.services.entities.DTO;

import ikcoder.services.entities.DTC.DTC_common;

import java.util.Map;

public class DTO_CSVHeader extends DTC_common {

    Map<String,String> mapCSVHeader;

    public Map<String, String> getMapCSVHeader() {
        return mapCSVHeader;
    }

    public void setMapCSVHeader(Map<String, String> mapCSVHeader) {
        this.mapCSVHeader = mapCSVHeader;
    }
}
