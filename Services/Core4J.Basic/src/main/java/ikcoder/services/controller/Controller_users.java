package ikcoder.services.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ikcoder.entities.coredb_basic.DTI.DTI_users;
import ikcoder.entities.coredb_basic.DTI.DTI_users_chpwd;
import ikcoder.entities.coredb_basic.DTO.DTO_common;
import ikcoder.entities.coredb_basic.DTO.DTO_users;
import ikcoder.entities.coredb_basic.DTO.DTO_users_id;
import ikcoder.entities.coredb_basic.DTO.DTO_users_inst;
import ikcoder.services.conf.*;
import ikcoder.services.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
public class Controller_users {

    Services_users _services_users;
    Services_messages _servicesMessages;
    Services_users_mapinfo _servicesUsers_mapinfo;
    Services_users_inst _services_users_inst;
    Services_inst _services_inst;
    StringRedisTemplate _redisTemplate;
    HttpServletRequest _httpServletRequest;
    HttpServletResponse _httpServletResponse;

    @Autowired
    public Controller_users(Services_users _services_users, Services_messages _servicesMessages, Services_users_mapinfo _servicesUsers_mapinfo, Services_users_inst _services_users_inst, Services_inst _services_inst, StringRedisTemplate _redisTemplate, HttpServletRequest _httpServletRequest, HttpServletResponse _httpServletResponse) {
        this._services_users = _services_users;
        this._servicesMessages = _servicesMessages;
        this._servicesUsers_mapinfo = _servicesUsers_mapinfo;
        this._services_users_inst = _services_users_inst;
        this._services_inst = _services_inst;
        this._redisTemplate = _redisTemplate;
        this._httpServletRequest = _httpServletRequest;
        this._httpServletResponse = _httpServletResponse;
    }

    @ResponseBody
    @PostMapping("/users")
    public DTO_common NewUser(@RequestBody DTI_users newUser)
    {
        if(_services_users.NewUser(newUser)) {
            this._servicesUsers_mapinfo.NewMapInfo(_services_users.SelectUserId(newUser).getId());
            return Services_common.newCommonResItem(_servicesMessages.GetMessage_code("8001"), "8001", false);
        }
        else {
            return Services_common.newCommonResItem(_servicesMessages.GetMessage_code("4001"), "4001", false);
        }
    }

    @ResponseBody
    @PostMapping("/users/login")
    public DTO_common Login(@RequestBody DTI_users DTI_users) throws Exception {
        DTO_common dto_common=new DTO_common();
        int returnCode = _services_users.VerifyUser(DTI_users);
        DTO_users dto_users = _services_users.GetUser(DTI_users);
        switch (returnCode) {
            case 0:
                return Services_common.newCommonResItem(_servicesMessages.GetMessage_code("4002"), "4002", false);
            case 1:
                List<DTO_users_inst> resultUsersInst=this._services_users_inst.getUsersInstMap(dto_users.getId());
                String inst_code;
                if(resultUsersInst.size()>0)
                {
                    inst_code = this._services_inst.GetFirstCode(resultUsersInst.get(0).getId());
                }
                else
                {
                    return Services_common.newCommonResItem( _servicesMessages.GetMessage_code("4003"), "4003", false);
                }
                HttpSession httpSession = this._httpServletRequest.getSession();
                UUID uuid = UUID.randomUUID();
                httpSession.setAttribute(Conf_symbol.symbol_token, uuid.toString());
                dto_users.setCode(inst_code);
                dto_users.setToken(uuid.toString());
                this._httpServletResponse.addHeader("token", uuid.toString());
                Services_common.writeUserIntoRedis(dto_users);
                return Services_common.newCommonResItem(_servicesMessages.GetMessage_code("8002"), "8002", false);
            case 2:
                return Services_common.newCommonResItem( _servicesMessages.GetMessage_code("4003"), "4003", false);
            case 3:
                return Services_common.newCommonResItem(_servicesMessages.GetMessage_code("4004"), "4004", false);
        }
        return dto_common;
    }

    @ResponseBody
    @PostMapping("/user/changepwd")
    public DTO_common ChangePwd(@RequestBody DTI_users_chpwd dti_users_chpwd)
    {
        if(dti_users_chpwd==null)
            return Services_common.newCommonResItem(_servicesMessages.GetMessage_code("4002"), "4002", false);
        else
        {
            this._services_users.ChangePwd(dti_users_chpwd);
            return Services_common.newCommonResItem(_servicesMessages.GetMessage_code("8005"), "8005", false);
        }
    }

    @ResponseBody
    @GetMapping("/users/info/{username}")
    public DTO_common GetUserIdByUsername(@PathVariable(name = "username")String username)
    {
        DTI_users dti_users = new DTI_users();
        dti_users.setUid(username);
        return Services_common.newCommonResItem(_services_users.SelectUserId(dti_users), "200", false);
    }

    @ResponseBody
    @GetMapping("/users/info/uid")
    public DTO_common GetUerIdByToken() throws Exception {
        HttpSession httpSession = this._httpServletRequest.getSession();
        if(this._httpServletRequest.getHeader("token")==null)
        {
            return Services_common.newCommonResItem(_servicesMessages.GetMessage_code("4008"), "4008", false);
        }
        String token = this._httpServletRequest.getHeader("token");
        String strJson = this._redisTemplate.opsForValue().get(token);
        if(strJson!=null && !strJson.isBlank()) {
            JSONObject jsonObject = JSON.parseObject(strJson);
            DTO_users dto_users = JSON.toJavaObject(jsonObject,DTO_users.class);
            DTO_users_id dto_users_id = new DTO_users_id();
            dto_users_id.setId(dto_users.getId());
            return Services_common.newCommonResItem(dto_users_id, "8002", false);
        }
        else
        {
            return Services_common.newCommonResItem(_servicesMessages.GetMessage_code("4006"), "4006", false);
        }
    }

    @ResponseBody
    @GetMapping("/users/verify")
    public DTO_common Verify() throws Exception {
        HttpSession httpSession = this._httpServletRequest.getSession();
        if(this._httpServletRequest.getHeader("token")==null)
        {
            return Services_common.newCommonResItem(_servicesMessages.GetMessage_code("4008"), "4008", false);
        }
        String token = this._httpServletRequest.getHeader("token");
        String strJson = this._redisTemplate.opsForValue().get(token);
        if(!strJson.isBlank()) {
            return Services_common.newCommonResItem(_servicesMessages.GetMessage_code("8004"), "8004", false);
        }
        else
        {
            return Services_common.newCommonResItem(_servicesMessages.GetMessage_code("4006"), "4006", false);
        }
    }



    @ResponseBody
    @GetMapping("/users/logout")
    public DTO_common Logout()
    {
        HttpSession httpSession = this._httpServletRequest.getSession();
        String sessionID = httpSession.getId();
        if(this._redisTemplate.opsForValue().get(sessionID)==sessionID)
        {
            httpSession.removeAttribute(Conf_symbol.symbol_token);
            this._redisTemplate.delete(sessionID);
            return Services_common.newCommonResItem(_servicesMessages.GetMessage_code("8003"), "8003", false);
        }
        else
        {
            return Services_common.newCommonResItem(_servicesMessages.GetMessage_code("4005"), "4005", false);
        }
    }

    @ResponseBody
    @GetMapping("/users/all")
    public List<DTO_users> SelectAllUsers() {
        List<DTO_users> usersList = _services_users.GetUsers();
        return usersList;
    }



}
