package ikcoder.services.docservices;

import ikcoder.services.docs.documents.DOC_inst;
import ikcoder.services.docs.documents.DOC_knowledges;
import ikcoder.services.docs.nodes.DOC_node_knowlege;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DocServices_knowledge extends DocServices_base{


    public DocServices_knowledge(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    public boolean isDocExisted(String docid_inst,String subject,String grade)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("docid_inst").is(docid_inst));
        query.addCriteria(Criteria.where("subject").is(subject));
        query.addCriteria(Criteria.where("grade").is(grade));
        DOC_knowledges doc_knowledges = (DOC_knowledges)mongoTemplate.findOne(query,DOC_knowledges.class);
        if(doc_knowledges!=null)
            return true;
        else
            return false;
    }

    public boolean newKnowledgesDoc(String docid_inst,String subject,String grade)
    {
        if(!isDocExisted(docid_inst,subject,grade)) {
            DOC_knowledges doc_knowledges = new DOC_knowledges();
            doc_knowledges.setDocid_inst(docid_inst);
            doc_knowledges.setGrade(grade);
            doc_knowledges.setSubject(subject);
            UUID doc_id = UUID.randomUUID();
            doc_knowledges.setId(doc_id.toString());
            return ActionSave(doc_knowledges);
        }
        else
            return false;

    }

    public DOC_knowledges selectKnowledgeDoc(String docid_inst,String subject,String grade)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("docid_inst").is(docid_inst));
        query.addCriteria(Criteria.where("subject").is(subject));
        query.addCriteria(Criteria.where("grade").is(grade));
        DOC_knowledges doc_knowledges = (DOC_knowledges)mongoTemplate.findOne(query,DOC_knowledges.class);
        if(doc_knowledges!=null)
            return doc_knowledges;
        else
            return null;
    }

    public boolean insertKnowledgeNode(String docid_inst,String subject,String grade,String title,List<String> connection)
    {
        Query query = new Query();
        query.addCriteria(Criteria.where("docid_inst").is(docid_inst));
        query.addCriteria(Criteria.where("subject").is(subject));
        query.addCriteria(Criteria.where("grade").is(grade));
        DOC_knowledges doc_knowledges = selectKnowledgeDoc(docid_inst,subject,grade);
        if(doc_knowledges==null)
        {
            newKnowledgesDoc(docid_inst,subject,grade);
            doc_knowledges = selectKnowledgeDoc(docid_inst,subject,grade);
        }
        DOC_node_knowlege doc_node_knowlege=new DOC_node_knowlege();
        doc_node_knowlege.setConnection(connection);
        UUID nodeID = UUID.randomUUID();
        doc_node_knowlege.setId(nodeID.toString());
        doc_node_knowlege.setTitle(title);
        doc_node_knowlege.setSubject(subject);
        doc_node_knowlege.setConnection(connection);
        doc_knowledges.getKnowledgePool().put(nodeID.toString(),doc_node_knowlege);
        Update update = new Update();
        update.set("lstClass",doc_knowledges.getClass());
        mongoTemplate.updateFirst(query,update, DOC_node_knowlege.class);
        return true;
    }


}
