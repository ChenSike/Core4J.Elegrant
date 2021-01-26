package ikcoder.services.docservices;


import ikcoder.services.docs.documents.DOC_inst;
import ikcoder.services.docs.nodes.DOC_node_class;
import ikcoder.services.docs.nodes.DOC_node_student;
import ikcoder.services.services.Services_common;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class DocServices_inst extends DocServices_base {

    @Autowired
    public DocServices_inst(MongoTemplate mongoTemplate) {
        super(mongoTemplate);
    }

    public boolean NewInst(String inst_code,String docid_inst)
    {
        DOC_inst doc_inst=new DOC_inst();
        doc_inst.setInst_code(inst_code);
        doc_inst.setDocid_inst(docid_inst);
        return ActionSave(doc_inst);
    }

    public boolean NewClasses(String docid_inst,String classname,Integer owner_id,String startYear)
    {
        Query query = new Query(Criteria.where("docid_inst").is(docid_inst));
        DOC_inst doc_inst = (DOC_inst) mongoTemplate.findOne(query, DOC_inst.class);
        if(doc_inst!=null)
        {
            if(!doc_inst.isClassExisted(classname, Services_common.getCurrentYear().toString()))
            {
                UUID uid_class = UUID.randomUUID();
                DOC_node_class doc_node_class=new DOC_node_class();
                doc_node_class.setName(classname);
                doc_node_class.setClassid(uid_class.toString());
                doc_node_class.setStartyear(Services_common.getCurrentYear().toString());
                doc_node_class.setIsgoing(true);
                doc_node_class.setUid_owner(owner_id);
                doc_node_class.setStartyear(startYear);
                doc_inst.getLstClass().add(doc_node_class);
                Update update = new Update();
                update.set("lstClass",doc_inst.getLstClass());
                mongoTemplate.updateFirst(query,update, DOC_inst.class);
                return true;
            }
            else
            {
                return false;
            }
        }
        else
            return false;
    }


    public boolean NewStudent(String docid_inst,String class_id, String number,String name,String gender,String name_father,String name_mother,String number_tel)
    {
        Query query = new Query(Criteria.where("docid_inst").is(docid_inst));
        DOC_inst doc_inst = (DOC_inst) mongoTemplate.findOne(query, DOC_inst.class);
        if(doc_inst!=null)
        {
            List<DOC_node_class> tmpResult = doc_inst.getLstClass().stream().filter((e)->e.getClassid()==class_id).collect(Collectors.toList());
            if(tmpResult!=null && tmpResult.size()>0)
            {
                UUID docid_examscore = UUID.randomUUID();
                UUID docid_homeworkscore = UUID.randomUUID();
                UUID docid_exam = UUID.randomUUID();
                UUID docid_homework = UUID.randomUUID();
                UUID id_student = UUID.randomUUID();
                DOC_node_student newStudentItem=new DOC_node_student();
                newStudentItem.setNumber(number);
                newStudentItem.setGender(gender);
                newStudentItem.setName(name);
                newStudentItem.setNmae_father(name_father);
                newStudentItem.setName_mother(name_mother);
                newStudentItem.setNumber_tel(number_tel);
                newStudentItem.setDocid_exam(docid_exam.toString());
                newStudentItem.setDocid_examscore(docid_examscore.toString());
                newStudentItem.setDocid_homewokscore(docid_homeworkscore.toString());
                newStudentItem.setDocid_homework(docid_homework.toString());
                tmpResult.get(0).getLstStudents().add(newStudentItem);
                Update update = new Update();
                update.set("lstClass",doc_inst.getLstClass());
                mongoTemplate.updateFirst(query,update, DOC_inst.class);
                return true;
            }
            else
                return false;
        }
        else
            return false;
    }

    public boolean SwitchClassIntoOff(String docid_inst, String uid_class)
    {
        Query query = new Query(Criteria.where("docid_inst").is(docid_inst));
        DOC_inst doc_inst = (DOC_inst) mongoTemplate.findOne(query, DOC_inst.class);
        if(doc_inst!=null) {
            DOC_node_class doc_node_class = doc_inst.getClass(uid_class);
            doc_node_class.setIsgoing(false);
            Update update = new Update();
            update.set("lstClass", doc_inst.getLstClass());
            mongoTemplate.updateFirst(query, update, DOC_inst.class);
            return true;
        }
        else
            return false;
    }

    public DOC_inst GetInstDocument(String docid_inst)
    {
        Query query = new Query(Criteria.where("docid_inst").is(docid_inst));
        DOC_inst doc_inst = (DOC_inst) mongoTemplate.findOne(query, DOC_inst.class);
        return doc_inst;
    }

}

