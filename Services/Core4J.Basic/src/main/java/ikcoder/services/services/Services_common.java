package ikcoder.services.services;

import com.alibaba.druid.sql.ast.statement.SQLAlterTableTouch;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import de.siegmar.fastcsv.reader.CsvContainer;
import de.siegmar.fastcsv.reader.CsvReader;
import de.siegmar.fastcsv.reader.CsvRow;
import ikcoder.services.conf.Conf_common;
import ikcoder.services.entities.DTC.DTC_common;
import ikcoder.services.entities.DTO.DTO_common;
import ikcoder.services.entities.DTO.DTO_common_message;
import ikcoder.services.entities.DTO.DTO_users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
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

    public static DTO_common newCommonResItem(DTC_common contentObj, String code, Boolean isExceptioned,Boolean isSucceeded)
    {
        DTO_common DTO_common = new DTO_common();
        DTO_common.setExceptioned(isExceptioned);
        DTO_common.setCode(code);
        DTO_common.setContent(contentObj);
        DTO_common.setSucceeded(isSucceeded);
        return DTO_common;
    }

    public static Map<String,String> GetCVSHeaderMap(String filePath) throws IOException {
        File file = new File(filePath);
        CsvReader csvReader = new CsvReader();
        CsvContainer csv = csvReader.read(file, StandardCharsets.UTF_8);
        List<String> headers = csv.getHeader();
        Integer headerIndex = 0;
        Map<String,String> resultHeaders = new HashMap<>();
        for (String tmpHeader:headers)
        {
            resultHeaders.put(headerIndex.toString(),tmpHeader);
            headerIndex++;
        }
        return resultHeaders;
    }

    public static List<Map<String,String>> GetCVSResultForName(String filePath) throws IOException {
        List<Map<String,String>> returnResult = new ArrayList<>();
        File file = new File(filePath);
        CsvReader csvReader = new CsvReader();
        CsvContainer csv = csvReader.read(file, StandardCharsets.UTF_8);
        List<CsvRow> dataRows = csv.getRows();
        List<String> lstHeader = csv.getHeader();
        for(CsvRow tmpRow:dataRows)
        {
            Map<String,String> rowItem = new HashMap<>();
            for (String strHeader:lstHeader)
            {
                rowItem.put(strHeader,tmpRow.getField(strHeader));
            }
            returnResult.add(rowItem);
        }
        return returnResult;
    }

    public static List<Map<String,String>> GetCVSResultForIndex(String filePath) throws IOException {
        List<Map<String,String>> returnResult = new ArrayList<>();
        File file = new File(filePath);
        CsvReader csvReader = new CsvReader();
        CsvContainer csv = csvReader.read(file, StandardCharsets.UTF_8);
        List<CsvRow> dataRows = csv.getRows();
        for(CsvRow tmpRow:dataRows)
        {
            Integer headerIndex = 0;
            Map<String,String> rowItem = new HashMap<>();
            for(;headerIndex<tmpRow.getFieldCount();headerIndex++)
            {
                rowItem.put(headerIndex.toString(),tmpRow.getField(headerIndex));
            }
            returnResult.add(rowItem);
        }
        return returnResult;
    }

    public static boolean ClearUploadTmpFile(String filePath)
    {
        try {
            File file = new File(filePath);
            file.delete();
            return true;
        }
        catch (Exception err)
        {
            return false;
        }
    }

    public static DTO_common newCommonResStringItem(String message, String code, Boolean isExceptioned,Boolean isSucceeded)
    {
        DTO_common_message dto_common_message = new DTO_common_message();
        dto_common_message.setMessage(message);
        DTO_common DTO_common = new DTO_common();
        DTO_common.setExceptioned(isExceptioned);
        DTO_common.setCode(code);
        DTO_common.setSucceeded(isSucceeded);
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

    public static String getTmpPoolPath()
    {
        return "tmppool";
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

    public static String getCurrentDate(){return Calendar.getInstance().get(Calendar.YEAR)+"-"+(Calendar.getInstance().get(Calendar.MONTH)+1)+"-"+Calendar.getInstance().get(Calendar.DAY_OF_MONTH);}

    public static Date getCurrentDT()
    {
        return Calendar.getInstance().getTime();
    }

}
