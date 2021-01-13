package ikcoder.services.controller;

import ikcoder.entities.coredb_basic.DTI.DTI_inst;
import ikcoder.entities.coredb_basic.DTO.DTO_common;
import ikcoder.services.services.Services_common;
import ikcoder.services.services.Services_inst;
import ikcoder.services.services.Services_messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller_inst {

    Services_inst services_inst;
    Services_messages services_messages;

    @Autowired
    public Controller_inst(Services_inst services_inst) {
        this.services_inst = services_inst;
    }

    @PostMapping("/init")
    @ResponseBody
    public DTO_common NewInst(@RequestBody DTI_inst dti_inst)
    {
        if(services_inst.NewItem(dti_inst.getName(),dti_inst.getAddress(),dti_inst.getType(),dti_inst.getCity(),dti_inst.getCode()))
        {
            return Services_common.newCommonResItem(services_messages.GetMessage_code("8001"), "8001", false);
        }
        else
        {
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true);
        }
    }

}
