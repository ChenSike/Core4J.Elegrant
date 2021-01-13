package ikcoder.services.services;

import ikcoder.entities.coredb_basic.DTO.DTO_message;
import ikcoder.services.mappers.Mapper_messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Services_messages {

    private Mapper_messages _mapper_messages;

    @Autowired
    public Services_messages(Mapper_messages _mapper_messages) {
        this._mapper_messages = _mapper_messages;
    }

    public DTO_message GetMessage_code(String code)
    {
        return _mapper_messages.Select_message_code(code);
    }

}
