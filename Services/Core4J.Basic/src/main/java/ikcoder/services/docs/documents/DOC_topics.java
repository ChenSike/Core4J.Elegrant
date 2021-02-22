package ikcoder.services.docs.documents;

import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;

@Document("topics")
public class DOC_topics extends DOC_base{

    String docid_inst;
    String id;
    String subject;
    String grade;
    Map<String,String> topics_level_map;


}
