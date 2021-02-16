package ikcoder.services.controller;

import ikcoder.services.docs.documents.DOC_inst;
import ikcoder.services.docs.nodes.DOC_node_class;
import ikcoder.services.docs.nodes.DOC_node_student;
import ikcoder.services.docservices.DocServices_inst;
import ikcoder.services.docservices.DocServices_userpf;
import ikcoder.services.entities.DTI.DTI_inst_student;
import ikcoder.services.entities.DTI.DTI_inst_student_import_rmap;
import ikcoder.services.entities.DTI.DTI_inst_student_removed;
import ikcoder.services.entities.DTO.*;
import ikcoder.services.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class Controller_dataStudents {

    Services_inst services_inst;
    Services_inst_mapinfo services_inst_mapinfo;
    Services_messages services_messages;
    DocServices_inst docServices_inst;
    DocServices_userpf docServices_userpf;
    StringRedisTemplate redisTemplate;
    Services_users_basicinfo services_users_basicinfo;
    Services_users_mapinfo services_users_mapinfo;

    @Autowired
    public Controller_dataStudents(Services_inst services_inst, Services_inst_mapinfo services_inst_mapinfo, Services_messages services_messages, DocServices_inst docServices_inst, DocServices_userpf docServices_userpf, StringRedisTemplate redisTemplate, Services_users_basicinfo services_users_basicinfo, Services_users_mapinfo services_users_mapinfo) {
        this.services_inst = services_inst;
        this.services_inst_mapinfo = services_inst_mapinfo;
        this.services_messages = services_messages;
        this.docServices_inst = docServices_inst;
        this.docServices_userpf = docServices_userpf;
        this.redisTemplate = redisTemplate;
        this.services_users_basicinfo = services_users_basicinfo;
        this.services_users_mapinfo = services_users_mapinfo;
    }

    @PostMapping("/data/students/new/{classid}")
    @ResponseBody
    public DTO_common NewStudent(@PathVariable(name="classid") String classid, @RequestBody DTI_inst_student dti_inst_student)
    {
        try {
            DTO_users dto_users = Services_common.getUserFromRedis();
            String inst_code = dto_users.getCode();
            DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
            DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
            docServices_inst.NewStudent(doc_inst, doc_inst.getDocid_inst(), classid, dti_inst_student.getNumber(), dti_inst_student.getName(), dti_inst_student.getGender(), dti_inst_student.getName_father(), dti_inst_student.getName_mother(), dti_inst_student.getNumber_tel());
            return Services_common.newCommonResItem(services_messages.GetMessage_code("8001"), "8001", false,true);
        }
        catch (Exception err)
        {
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true,false);
        }
    }

    @GetMapping("/data/students/list/{classid}")
    @ResponseBody
    public DTO_common ListStudent(@PathVariable(name="classid") String classid)
    {
        try {
            DTO_users dto_users = Services_common.getUserFromRedis();
            String inst_code = dto_users.getCode();
            DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
            DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
            List<DOC_node_student> lstResult=docServices_inst.ListStudents(doc_inst,doc_inst.getDocid_inst(),classid);
            DTO_data_dashbord_students dto_data_dashbord_students=new DTO_data_dashbord_students();
            dto_data_dashbord_students.setLstStudents(lstResult);
            return Services_common.newCommonResItem(dto_data_dashbord_students ,"8001", false,true);
        }
        catch (Exception err)
        {
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true,false);
        }
    }

    @GetMapping("/data/students/select/{classid}")
    @ResponseBody
    public DTO_common SelectStudent(@PathVariable String classid)
    {
        try {
            if(classid.isEmpty())
                return Services_common.newCommonResItem(services_messages.GetMessage_code("4002"), "4002", false,false);
            else
            {
                DTO_users dto_users = Services_common.getUserFromRedis();
                String inst_code = dto_users.getCode();
                DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
                DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
                DOC_node_student doc_node_student= docServices_inst.SelectStudent(doc_inst,doc_inst.getDocid_inst(),classid);
                DTO_data_dashbord_student dto_data_dashbord_student = new DTO_data_dashbord_student();
                dto_data_dashbord_student.setDoc_node_student(doc_node_student);
                return Services_common.newCommonResItem(dto_data_dashbord_student ,"8001", false,true);
            }

        }
        catch (Exception err)
        {
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true,false);
        }
    }

    @PostMapping("/data/students/remove")
    @ResponseBody
    public DTO_common RemoveStudent(@RequestBody DTI_inst_student_removed dti_inst_student_removed)
    {
        try {
            DTO_users dto_users = Services_common.getUserFromRedis();
            String inst_code = dto_users.getCode();
            DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
            DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
            docServices_inst.RemoveStudent(doc_inst,dti_inst_student_removed.getClass_id(),doc_inst.getDocid_inst());
            return Services_common.newCommonResItem(services_messages.GetMessage_code("8001"), "8001", false,true);
        }
        catch (Exception err)
        {
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true,false);
        }
    }

    @GetMapping("/data/students/struct")
    @ResponseBody
    public DTO_common GetStudentStruct()
    {
        DTO_data_dashbord_studentstruct dto_data_dashbord_studentstruct = new DTO_data_dashbord_studentstruct();
        dto_data_dashbord_studentstruct.getStructAttributes().add("number");
        dto_data_dashbord_studentstruct.getStructAttributes().add("name");
        dto_data_dashbord_studentstruct.getStructAttributes().add("gender");
        dto_data_dashbord_studentstruct.getStructAttributes().add("name_father");
        dto_data_dashbord_studentstruct.getStructAttributes().add("name_mother");
        dto_data_dashbord_studentstruct.getStructAttributes().add("number_tel");
        return Services_common.newCommonResItem(dto_data_dashbord_studentstruct ,"8001", false,true);
    }

    @GetMapping("/data/students/import")
    @ResponseBody
    public DTO_common SetImportFromCSV(DTI_inst_student_import_rmap dti_inst_student_import_rmap)
    {
        try {
            DTO_users dto_users = Services_common.getUserFromRedis();
            String inst_code = dto_users.getCode();
            DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
            DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
            String currentTmpFile = docServices_userpf.GetCurrentUploadFileName(dto_users.getUid());
            String filePath = Services_common.getTmpPoolPath() + "\\" + currentTmpFile;
            List<Map<String,String>> CSVResult = Services_common.GetCVSResultForName(filePath);
            if(CSVResult!=null && CSVResult.size()>0) {
                for (Map<String, String> CSVResultItem : CSVResult) {
                    String name_value = "";
                    String gender_value = "";
                    String name_father_value="";
                    String name_mother_value="";
                    String number_tel_value="";
                    String number_value="";
                    String source_value="";
                    for (String sourceName : dti_inst_student_import_rmap.getRMap_name().keySet()) {
                        String targetName = dti_inst_student_import_rmap.getRMap_name().get(sourceName);
                        source_value = CSVResultItem.get(sourceName).toString();
                        switch (targetName) {
                            case "name":
                                name_value = source_value;
                                break;
                            case "gender":
                                gender_value = source_value;
                                break;
                            case "name_father":
                                name_father_value = source_value;
                                break;
                            case "name_mother":
                                name_mother_value = source_value;
                                break;
                            case "number_tel_value":
                                number_tel_value = source_value;
                                break;
                            case "number":
                                number_value = source_value;
                                break;
                        }
                    }
                    docServices_inst.NewStudent(doc_inst, doc_inst.getDocid_inst(), dti_inst_student_import_rmap.getClassid(),number_value,name_value,gender_value,name_father_value,name_mother_value,number_tel_value);
                }
                return Services_common.newCommonResItem(services_messages.GetMessage_code("8001"), "8001", false,true);
            }
            else
                return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true,false);
        }
        catch (Exception err)
        {
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true,false);
        }
    }

    @GetMapping("/data/students/auto-number/{classid}")
    @ResponseBody
    public DTO_common GetAutoNumber(@PathVariable(name="classid") String classid) {
        try {

            DTO_users dto_users = Services_common.getUserFromRedis();
            String inst_code = dto_users.getCode();
            DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
            DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
            Stream<DOC_node_class> stream_Doc_node_class = doc_inst.getLstClass().stream();
            Stream<DOC_node_class> filter_Doc_node_class = stream_Doc_node_class.filter((e) -> e.getUid_owner() == dto_users.getId() && e.getClassid() == classid);
            List<DOC_node_class> lst_Doc_node_class = filter_Doc_node_class.collect(Collectors.toList());
            Integer sizeValue = lst_Doc_node_class.get(0).getLstStudents().size();
            return Services_common.newCommonResStringItem(sizeValue.toString(), "8001", false,true);
        }
        catch (Exception err)
        {
            return Services_common.newCommonResStringItem(err.getMessage(), "4001", true,false);
        }
    }

}
