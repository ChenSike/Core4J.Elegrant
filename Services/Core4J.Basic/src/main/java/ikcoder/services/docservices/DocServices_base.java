package ikcoder.services.docservices;

import ikcoder.services.docs.documents.DOC_base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

@Component
public class DocServices_base {

    protected MongoTemplate mongoTemplate;

    @Autowired
    public DocServices_base(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public Boolean ActionSave(DOC_base docEntity)
    {
        try
        {
            mongoTemplate.insert(docEntity);
            return true;
        }
        catch (Exception e)
        {
            return false;
        }
    }

}
