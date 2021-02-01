package ikcoder.services.mappers;


import ikcoder.services.entities.DTO.DTO_message;

public interface Mapper_messages {

    public DTO_message Select_message_message(String message);

    public DTO_message Select_message_code(String code);

}
