package ikcoder.services.mappers;

import ikcoder.entities.coredb_basic.DTO.DTO_message;

public interface Mapper_messages {

    public DTO_message Select_message_message(String message);

    public DTO_message Select_message_code(String code);

}
