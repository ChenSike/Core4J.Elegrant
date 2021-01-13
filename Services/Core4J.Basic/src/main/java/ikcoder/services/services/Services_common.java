package ikcoder.services.services;

import ikcoder.entities.coredb_basic.DTC.DTC_common;
import ikcoder.entities.coredb_basic.DTO.DTO_common;
import org.springframework.stereotype.Component;

@Component
public class Services_common {

    public static DTO_common newCommonResItem(DTC_common contentObj, String code, Boolean isExceptioned)
    {
        DTO_common DTO_common = new DTO_common();
        DTO_common.setExceptioned(isExceptioned);
        DTO_common.setCode(code);
        DTO_common.setContent(contentObj);
        return DTO_common;
    }

}
