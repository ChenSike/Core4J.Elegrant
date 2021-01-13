package ikcoder.services.controller;

import ikcoder.entities.coredb_basic.DTO.DTO_users_mapinfoAppid;
import ikcoder.entities.coredb_basic.DTO.DTO_users_mapinfoDocid;
import ikcoder.services.services.Services_messages;
import ikcoder.services.services.Services_users;
import ikcoder.services.services.Services_users_mapinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller_user_mapinfo {

    Services_users _services_users;
    Services_messages _servicesMessages;
    Services_users_mapinfo _servicesUsers_mapinfo;

    @Autowired
    public Controller_user_mapinfo(Services_users _services_users, Services_messages _servicesMessages, Services_users_mapinfo _servicesUsers_mapinfo) {
        this._services_users = _services_users;
        this._servicesMessages = _servicesMessages;
        this._servicesUsers_mapinfo = _servicesUsers_mapinfo;
    }

    @ResponseBody
    @GetMapping("/user_mapinfo/docid/{uid}")
    public DTO_users_mapinfoDocid GetDocId(@PathVariable(name = "uid")Integer uid)
    {
        DTO_users_mapinfoDocid DTO_users_mapinfoDocid = new DTO_users_mapinfoDocid();
        DTO_users_mapinfoDocid.setDocid(this._servicesUsers_mapinfo.GetDocId(uid));
        return DTO_users_mapinfoDocid;
    }

    @ResponseBody
    @GetMapping("/user_mapinfo/appid/{uid}")
    public DTO_users_mapinfoAppid GetAppId(@PathVariable(name = "uid")Integer uid)
    {
        DTO_users_mapinfoAppid DTO_users_mapinfoappid = new DTO_users_mapinfoAppid();
        DTO_users_mapinfoappid.setAppid(this._servicesUsers_mapinfo.GetAppId(uid));
        return DTO_users_mapinfoappid;
    }


}
