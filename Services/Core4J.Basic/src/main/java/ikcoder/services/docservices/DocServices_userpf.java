package ikcoder.services.docservices;

import ikcoder.services.docs.documents.DOC_userpf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class DocServices_userpf extends DocServices_base{


    @Autowired
    public DocServices_userpf(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    public boolean NewUserpf(Integer uid,String docid_userfp)
    {
        try {
            DOC_userpf newUserPFDoc = new DOC_userpf();
            newUserPFDoc.setDocid_userpf(docid_userfp);
            newUserPFDoc.setUid(uid);
            ActionSave(newUserPFDoc);
            return true;
        }
        catch (Exception err)
        {
            return false;
        }

    }

    public String GetCurrentUploadFileName(String uid)
    {
        Query query = new Query(Criteria.where("uid").is(uid));
        DOC_userpf doc_userpf = (DOC_userpf) mongoTemplate.findOne(query, DOC_userpf.class);
        if(doc_userpf!=null)
        {
            return doc_userpf.getTmppool_currentUploadFile();
        }
        else
            return "";
    }

    public boolean SetClearUploadFile(String uid)
    {
        Query query = new Query(Criteria.where("uid").is(uid));
        DOC_userpf doc_userpf = (DOC_userpf) mongoTemplate.findOne(query, DOC_userpf.class);
        if(doc_userpf!=null)
        {
            Update update = new Update();
            update.set("tmppool_currentUploadFile","");
            mongoTemplate.updateFirst(query,update, DOC_userpf.class);
            return true;
        }
        else
            return false;
    }

    public boolean SetCurrentUploadFile(String uid,String filename)
    {
        Query query = new Query(Criteria.where("uid").is(uid));
        DOC_userpf doc_userpf = (DOC_userpf) mongoTemplate.findOne(query, DOC_userpf.class);
        if(doc_userpf!=null)
        {
            Update update = new Update();
            update.set("tmppool_currentUploadFile",filename.isEmpty()? UUID.randomUUID().toString():filename);
            mongoTemplate.updateFirst(query,update, DOC_userpf.class);
            return true;
        }
        else
            return false;
    }

}
