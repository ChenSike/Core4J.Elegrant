package ikcoder.services.controller;


import ikcoder.entities.coredb_basic.DT.DT_users;
import ikcoder.entities.coredb_basic.DTI.DTI_users;
import ikcoder.entities.coredb_basic.DTO.DTO_common;
import ikcoder.services.services.Services_messages;
import ikcoder.services.services.Services_users;
import ikcoder.services.services.Services_users_mapinfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class Controller_vdata {

    class DTO_VData_users extends DTI_users
    {
        String docid;

        public String getDocid() {
            return docid;
        }

        public void setDocid(String docid) {
            this.docid = docid;
        }
    }

    Services_users _services_users;
    Services_messages _servicesMessages;
    Services_users_mapinfo _servicesUsers_mapinfo;

    @Autowired
    public Controller_vdata(Services_users _services_users, Services_messages _servicesMessages, Services_users_mapinfo _servicesUsers_mapinfo) {
        this._services_users = _services_users;
        this._servicesMessages = _servicesMessages;
        this._servicesUsers_mapinfo = _servicesUsers_mapinfo;
    }

    @ResponseBody
    @GetMapping("/vdata/users/new/r-detail/{count}")
    public List<DTO_VData_users> VDataNewUsersForDetail(@PathVariable int count)
    {
        List<DTO_VData_users> lstPool = new ArrayList<>();
        for(int i=1;i<=count;i++)
        {
            DTI_users newUser = new DTI_users();
            Random random=new Random();
            Integer val = random.nextInt(999999);
            String tmpUserName = "testuser_"+val.toString();
            String tmpPassword = "12345678";
            newUser.setUid(tmpUserName);
            newUser.setPwd(tmpPassword);
            this._services_users.NewUser(newUser);
            this._servicesUsers_mapinfo.NewMapInfo(_services_users.SelectUserId(newUser).getId());
            DTO_VData_users dto_vData_users = new DTO_VData_users();
            dto_vData_users.setUid(tmpUserName);
            dto_vData_users.setDocid(this._servicesUsers_mapinfo.GetDocId(_services_users.SelectUserId(newUser).getId()));
            dto_vData_users.setId(_services_users.SelectUserId(newUser).getId());
            lstPool.add(dto_vData_users);
        }
        return lstPool;
    }

    @ResponseBody
    @GetMapping("/vdata/users/new/r-list/{count}")
    public List<String> VDataNewUsersForList(@PathVariable int count)
    {
        List<String> lstPool = new ArrayList<>();
        for(int i=1;i<=count;i++)
        {
            DTI_users newUser = new DTI_users();
            Random random=new Random();
            Integer val = random.nextInt(999999);
            String tmpUserName = "testuser_"+val.toString();
            String tmpPassword = "12345678";
            newUser.setUid(tmpUserName);
            newUser.setPwd(tmpPassword);
            this._services_users.NewUser(newUser);
            this._servicesUsers_mapinfo.NewMapInfo(_services_users.SelectUserId(newUser).getId());
            lstPool.add(this._servicesUsers_mapinfo.GetDocId(_services_users.SelectUserId(newUser).getId()));

        }
        return lstPool;
    }

}
