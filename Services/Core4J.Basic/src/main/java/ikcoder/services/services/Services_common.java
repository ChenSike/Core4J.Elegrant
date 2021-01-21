package ikcoder.services.services;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import ikcoder.entities.coredb_basic.DTC.DTC_common;
import ikcoder.entities.coredb_basic.DTO.DTO_common;
import ikcoder.entities.coredb_basic.DTO.DTO_common_message;
import ikcoder.entities.coredb_basic.DTO.DTO_users;
import ikcoder.services.conf.Conf_common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class Services_common {

    static StringRedisTemplate _redisTemplate;
    static HttpServletRequest _httpServletRequest;
    static HttpServletResponse _httpServletResponse;


    @Autowired
    public void set_redisTemplate(StringRedisTemplate _redisTemplate) {
        Services_common._redisTemplate = _redisTemplate;
    }

    @Autowired
    public void set_httpServletRequest(HttpServletRequest _httpServletRequest) {
        Services_common._httpServletRequest = _httpServletRequest;
    }

    @Autowired
    public void set_httpServletResponse(HttpServletResponse _httpServletResponse) {
        Services_common._httpServletResponse = _httpServletResponse;
    }

    public static DTO_common newCommonResItem(DTC_common contentObj, String code, Boolean isExceptioned)
    {
        DTO_common DTO_common = new DTO_common();
        DTO_common.setExceptioned(isExceptioned);
        DTO_common.setCode(code);
        DTO_common.setContent(contentObj);
        return DTO_common;
    }

    public static DTO_common newCommonResStringItem(String message, String code, Boolean isExceptioned)
    {
        DTO_common_message dto_common_message = new DTO_common_message();
        dto_common_message.setMessage(message);
        DTO_common DTO_common = new DTO_common();
        DTO_common.setExceptioned(isExceptioned);
        DTO_common.setCode(code);
        DTO_common.setContent(dto_common_message);
        return DTO_common;
    }

    public static void writeUserIntoRedis(DTO_users dto_users)
    {
        String strJson = JSON.toJSONString(dto_users);
        _redisTemplate.opsForValue().set(dto_users.getToken(), strJson, Conf_common.timeout_session, TimeUnit.SECONDS);
    }

    public static DTO_users getUserFromRedis()
    {
        String token = _httpServletRequest.getHeader("token");
        String strJson = _redisTemplate.opsForValue().get(token);
        if(strJson!=null && !strJson.isBlank()) {
            JSONObject jsonObject = JSON.parseObject(strJson);
            DTO_users dto_users = JSON.toJavaObject(jsonObject, DTO_users.class);
            return dto_users;
        }
        else
            return null;
    }

    public static String getInstCode()
    {
        return getUserFromRedis().getCode();
    }

    public static Integer getCurrentYear()
    {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    public static Integer getCurrentMonth()
    {
        return Calendar.getInstance().get(Calendar.MONTH)+1;
    }

    public static Integer getCurrentDay()
    {
        return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
    }

    public static Date getCurrentDT()
    {
        return Calendar.getInstance().getTime();
    }

}
