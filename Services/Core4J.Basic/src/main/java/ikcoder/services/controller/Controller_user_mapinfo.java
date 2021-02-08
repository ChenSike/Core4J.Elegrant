package ikcoder.services.controller;

import ikcoder.services.entities.DTO.DTO_common;
import ikcoder.services.services.Services_common;
import ikcoder.services.services.Services_messages;
import ikcoder.services.services.Services_users;
import ikcoder.services.services.Services_users_mapinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class Controller_user_mapinfo {

    Services_users services_users;
    Services_messages services_messages;
    Services_users_mapinfo services_users_mapinfo;

    @Autowired
    public Controller_user_mapinfo(Services_users services_users, Services_messages services_messages, Services_users_mapinfo services_users_mapinfo) {
        this.services_users = services_users;
        this.services_messages = services_messages;
        this.services_users_mapinfo = services_users_mapinfo;
    }

    @GetMapping("/user_mapinfo/new/{uid}")
    @ResponseBody
    public DTO_common NewUserMapInfo(@PathVariable(name = "uid") Integer uid)
    {
        services_users_mapinfo.NewMapInfo(uid);
        return Services_common.newCommonResItem(services_messages.GetMessage_code("8001"), "8001", false,true);
    }

}
