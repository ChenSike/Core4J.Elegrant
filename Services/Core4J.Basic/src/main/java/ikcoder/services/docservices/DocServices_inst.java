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

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    public boolean RemoveClass(String docid_inst,String classid)
    {
        try {
            Query query = new Query(Criteria.where("docid_inst").is(docid_inst));
            DOC_inst doc_inst = (DOC_inst) mongoTemplate.findOne(query, DOC_inst.class);
            DOC_node_class doc_node_class = doc_inst.getClass(classid);
            doc_inst.getLstClass().remove(doc_node_class);
            Update update = new Update();
            update.set("lstClass", doc_inst.getLstClass());
            mongoTemplate.updateFirst(query, update, DOC_inst.class);
            return true;
        }
        catch (Exception err)
        {
            return false;
        }
    }

    public List<DOC_node_student> ListStudents(DOC_inst doc_inst,String docid_inst,String class_id) {
        List<DOC_node_student> lstStudent = new ArrayList<>();
        if (doc_inst != null) {
            Stream<DOC_node_class> stream_Doc_node_class = doc_inst.getLstClass().stream();
            Stream<DOC_node_class> filter_Doc_node_class = stream_Doc_node_class.filter((e) -> e.getClassid().equals(class_id));
            List<DOC_node_class> tmpResult = filter_Doc_node_class.collect(Collectors.toList());
            if (tmpResult != null && tmpResult.size() > 0) {
                lstStudent = tmpResult.get(0).getLstStudents();
            }
        }
        return lstStudent;
    }

    public DOC_node_student SelectStudent(DOC_inst doc_inst,String docid_inst,String class_id)
    {
        if (doc_inst != null) {
            Stream<DOC_node_class> stream_Doc_node_class = doc_inst.getLstClass().stream();
            Stream<DOC_node_class> filter_Doc_node_class = stream_Doc_node_class.filter((e) -> e.getClassid().equals(class_id));
            List<DOC_node_class> tmpResult = filter_Doc_node_class.collect(Collectors.toList());
            if (tmpResult != null && tmpResult.size() > 0) {
                List<DOC_node_student> lstStudent = tmpResult.get(0).getLstStudents();
                if (lstStudent != null && lstStudent.size() > 0) {
                    for (DOC_node_student activeNode : lstStudent) {
                        if (activeNode.getId().equals(class_id)) {
                            return activeNode;
                        }
                    }
                }
            }
        }
        return null;
    }


    public boolean RemoveStudent(DOC_inst doc_inst,String class_id,String docid_inst)
    {
        if (doc_inst != null) {
            Query query = new Query(Criteria.where("docid_inst").is(docid_inst));
            Stream<DOC_node_class> stream_Doc_node_class = doc_inst.getLstClass().stream();
            Stream<DOC_node_class> filter_Doc_node_class = stream_Doc_node_class.filter((e) -> e.getClassid().equals(class_id));
            List<DOC_node_class> tmpResult = filter_Doc_node_class.collect(Collectors.toList());
            if (tmpResult != null && tmpResult.size() > 0) {
                List<DOC_node_student> lstStudent = tmpResult.get(0).getLstStudents();
                if(lstStudent!=null && lstStudent.size()>0)
                {
                    for(DOC_node_student activeNode : lstStudent)
                    {
                        if(activeNode.getId().equals(class_id))
                        {
                            lstStudent.remove(activeNode);
                            break;
                        }
                    }
                    Update update = new Update();
                    update.set("lstClass", doc_inst.getLstClass());
                    mongoTemplate.updateFirst(query, update, DOC_inst.class);
                    return true;
                }
                else
                    return false;
            }
            else
            {
                return false;
            }
        }
        else
            return false;
    }


    public boolean NewStudent(DOC_inst doc_inst, String docid_inst,String class_id, String number,String name,String gender,String name_father,String name_mother,String number_tel) {
        if (doc_inst != null) {
            Query query = new Query(Criteria.where("docid_inst").is(docid_inst));
            Stream<DOC_node_class> stream_Doc_node_class = doc_inst.getLstClass().stream();
            Stream<DOC_node_class> filter_Doc_node_class = stream_Doc_node_class.filter((e) -> e.getClassid().equals(class_id));
            List<DOC_node_class> tmpResult = filter_Doc_node_class.collect(Collectors.toList());
            if (tmpResult != null && tmpResult.size() > 0) {
                if (tmpResult.get(0).getLstStudents() == null) {
                    List<DOC_node_student> newLstStudents = new ArrayList<>();
                    tmpResult.get(0).setLstStudents(newLstStudents);
                }
                UUID docid_examscore = UUID.randomUUID();
                UUID docid_homeworkscore = UUID.randomUUID();
                UUID docid_exam = UUID.randomUUID();
                UUID docid_homework = UUID.randomUUID();
                UUID id_student = UUID.randomUUID();
                DOC_node_student newStudentItem = new DOC_node_student();
                newStudentItem.setNumber(number);
                newStudentItem.setId(id_student.toString());
                newStudentItem.setGender(gender);
                newStudentItem.setName(name);
                newStudentItem.setName_father(name_father);
                newStudentItem.setName_mother(name_mother);
                newStudentItem.setNumber_tel(number_tel);
                newStudentItem.setDocid_exam(docid_exam.toString());
                newStudentItem.setDocid_examscore(docid_examscore.toString());
                newStudentItem.setDocid_homewokscore(docid_homeworkscore.toString());
                newStudentItem.setDocid_homework(docid_homework.toString());
                tmpResult.get(0).getLstStudents().add(newStudentItem);
                Update update = new Update();
                update.set("lstClass", doc_inst.getLstClass());
                mongoTemplate.updateFirst(query, update, DOC_inst.class);
                return true;
            } else
                return false;
        } else
            return false;
    }

    public boolean UpdateStudent(DOC_inst doc_inst, String docid_inst,String class_id, String student_id, String number,String name,String name_father,String name_mother,String number_tel) {
        if (doc_inst != null) {
            Query query = new Query(Criteria.where("docid_inst").is(docid_inst));
            Stream<DOC_node_class> stream_Doc_node_class = doc_inst.getLstClass().stream();
            Stream<DOC_node_class> filter_Doc_node_class = stream_Doc_node_class.filter((e) -> e.getClassid().equals(class_id));
            List<DOC_node_class> tmpResult = filter_Doc_node_class.collect(Collectors.toList());
            if (tmpResult != null && tmpResult.size() > 0) {
                if (tmpResult.get(0).getLstStudents() == null) {
                    return false;
                }
                List<DOC_node_student> lstStudents = tmpResult.get(0).getLstStudents().stream().filter((e)->e.getId().equals(student_id)).collect(Collectors.toList());
                DOC_node_student newStudentItem = lstStudents.get(0);
                if(!name.isEmpty())
                    newStudentItem.setName(name);
                if(!name_father.isEmpty())
                    newStudentItem.setName_father(name_father);
                if(!name_mother.isEmpty())
                    newStudentItem.setName_mother(name_mother);
                if(!number_tel.isEmpty())
                    newStudentItem.setNumber_tel(number_tel);
                tmpResult.get(0).getLstStudents().add(newStudentItem);
                Update update = new Update();
                update.set("lstClass", doc_inst.getLstClass());
                mongoTemplate.updateFirst(query, update, DOC_inst.class);
                return true;
            } else
                return false;
        } else
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

