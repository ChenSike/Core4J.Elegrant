package ikcoder.services.services;

import ikcoder.entities.coredb_basic.DTO.DTO_message;
import ikcoder.services.mappers.Mapper_messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Services_server {

    private Mapper_messages _mapper_messages;

    private String code_running = "1001";
    private String code_suspended = "1002";
    private String code_stop = "1003";

    @Autowired
    public Services_server(Mapper_messages mapper_messages)
    {
        this._mapper_messages = mapper_messages;
    }

    public HashMap<String,String> GetStatus()
    {
        HashMap<String,String> result=new HashMap<>();
        try {

            DTO_message DTO_message = _mapper_messages.Select_message_code(code_running);
            if (DTO_message != null) {
                result.put("status", DTO_message.getMessage());
                result.put("code", DTO_message.getMessage());
            } else {
                result.put("status", "stop");
                result.put("code", code_stop);
            }
        }
        catch (Exception err)
        {
            result.put("status", "stop");
            result.put("code", code_stop);
            result.put("msg",err.getMessage());
        }
        finally {
            return result;
        }
    }

}
