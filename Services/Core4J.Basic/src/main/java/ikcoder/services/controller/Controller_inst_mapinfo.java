package ikcoder.services.controller;

import ikcoder.services.entities.DTI.DTI_inst_mapinfo;
import ikcoder.services.entities.DTO.DTO_common;
import ikcoder.services.services.Services_common;
import ikcoder.services.services.Services_inst_mapinfo;
import ikcoder.services.services.Services_messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class Controller_inst_mapinfo {

    Services_inst_mapinfo services_inst_mapinfo;
    Services_messages services_messages;

    @Autowired
    public Controller_inst_mapinfo(Services_inst_mapinfo services_inst_mapinfo, Services_messages services_messages) {
        this.services_inst_mapinfo = services_inst_mapinfo;
        this.services_messages = services_messages;
    }

    @PostMapping("/instmapinfo")
    @ResponseBody
    public DTO_common NewInstmapinfo(@RequestBody DTI_inst_mapinfo dti_inst_mapinfo)
    {

        UUID uuid = UUID.randomUUID();
        services_inst_mapinfo.NewItem(dti_inst_mapinfo.getInst_code(),uuid.toString());
        return Services_common.newCommonResStringItem(uuid.toString(), "8001", false,true);
    }

}
