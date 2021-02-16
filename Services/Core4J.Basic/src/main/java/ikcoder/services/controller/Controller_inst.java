package ikcoder.services.controller;

import ikcoder.services.docs.documents.DOC_inst;
import ikcoder.services.docs.nodes.DOC_node_class;
import ikcoder.services.docs.nodes.DOC_node_student;
import ikcoder.services.docs.outs.documents.OUTS_inst_classes_class;
import ikcoder.services.docs.outs.documents.OUTS_inst_classes_matrix;
import ikcoder.services.docs.outs.nodes.OUTS_node_inst_classes_class;
import ikcoder.services.docservices.DocServices_inst;
import ikcoder.services.docservices.DocServices_userpf;
import ikcoder.services.entities.DT.DT_inst;
import ikcoder.services.entities.DT.DT_inst_class;
import ikcoder.services.entities.DTI.DTI_class;
import ikcoder.services.entities.DTI.DTI_inst;
import ikcoder.services.entities.DTI.DTI_inst_student;
import ikcoder.services.entities.DTO.DTO_common;
import ikcoder.services.entities.DTO.DTO_inst_class;
import ikcoder.services.entities.DTO.DTO_inst_mapinfo;
import ikcoder.services.entities.DTO.DTO_users;
import ikcoder.services.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class Controller_inst {

    Services_inst services_inst;
    Services_inst_mapinfo services_inst_mapinfo;
    Services_messages services_messages;
    DocServices_inst docServices_inst;
    DocServices_userpf docServices_userpf;
    StringRedisTemplate redisTemplate;
    Services_users_basicinfo services_users_basicinfo;
    Services_users_mapinfo services_users_mapinfo;

    @Autowired
    public Controller_inst(Services_inst services_inst, Services_inst_mapinfo services_inst_mapinfo, Services_messages services_messages, DocServices_inst docServices_inst, DocServices_userpf docServices_userpf, StringRedisTemplate redisTemplate, Services_users_basicinfo services_users_basicinfo, Services_users_mapinfo services_users_mapinfo) {
        this.services_inst = services_inst;
        this.services_inst_mapinfo = services_inst_mapinfo;
        this.services_messages = services_messages;
        this.docServices_inst = docServices_inst;
        this.docServices_userpf = docServices_userpf;
        this.redisTemplate = redisTemplate;
        this.services_users_basicinfo = services_users_basicinfo;
        this.services_users_mapinfo = services_users_mapinfo;
    }

    public Controller_inst() {
    }

    @PostMapping("/inst")
    @ResponseBody
    public DTO_common NewInst(@RequestBody DTI_inst dti_inst)
    {
        if(services_inst.NewItem(dti_inst.getName(),dti_inst.getAddress(),dti_inst.getType(),dti_inst.getCity(),dti_inst.getCode()))
        {
            UUID uuid = UUID.randomUUID();
            if(services_inst_mapinfo.NewItem(dti_inst.getCode(),uuid.toString())) {
                if (docServices_inst.NewInst(dti_inst.getCode(), uuid.toString())) {
                    return Services_common.newCommonResItem(services_messages.GetMessage_code("8001"), "8001", false,true);
                } else {
                    return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", false,false);
                }
            }
            else
                return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", false,false);
        }
        else
        {
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", false,false);
        }
    }

    @GetMapping("/inst/docs/new/{inst_code}")
    @ResponseBody
    public DTO_common NewInstDoc(@PathVariable String inst_code) {
        if(!services_inst_mapinfo.IsExisted(inst_code)) {
            UUID uuid = UUID.randomUUID();
            services_inst_mapinfo.NewItem(inst_code, uuid.toString());
            if (docServices_inst.NewInst(inst_code, uuid.toString())) {
                return Services_common.newCommonResItem(services_messages.GetMessage_code("8001"), "8001", false,true);
            } else {
                return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", false,false);
            }
        }
        else
        {
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", false,false);
        }
    }


    @PostMapping("/inst/class")
    @ResponseBody
    public DTO_common NewInstDoc(@RequestBody DTI_class dti_class) {
        DTO_users dto_users = Services_common.getUserFromRedis();
        String inst_code = dto_users.getCode();
        DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
        DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
        Integer uid_owner = dto_users.getId();
        if (docServices_inst.NewClasses(doc_inst.getDocid_inst(), dti_class.getName(), uid_owner, dti_class.getStartYear()))
            return Services_common.newCommonResItem(services_messages.GetMessage_code("8001"), "8001", false,true);
        else
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true,false);
    }






    @GetMapping("/inst/class/list")
    @ResponseBody
    public DTO_common GetClassList() {
        try {
            DTO_users dto_users = Services_common.getUserFromRedis();
            String inst_code = dto_users.getCode();
            DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
            DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
            Stream<DOC_node_class> stream_Doc_node_class = doc_inst.getLstClass().stream();
            Stream<DOC_node_class> filter_Doc_node_class = stream_Doc_node_class.filter((e) -> e.getUid_owner() == dto_users.getId());
            List<DOC_node_class> lst_Doc_node_class = filter_Doc_node_class.collect(Collectors.toList());
            doc_inst.setLstClass(lst_Doc_node_class);
            return Services_common.newCommonResItem(doc_inst, "8001", false,true);
        } catch (Exception err) {
            return Services_common.newCommonResStringItem(err.getMessage(), "4001", true,false);
        }
    }

    @GetMapping("/inst/class/index")
    @ResponseBody
    public DTO_common GetClassIndex() {
        try {
            DTO_users dto_users = Services_common.getUserFromRedis();
            String inst_code = dto_users.getCode();
            DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
            DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
            Stream<DOC_node_class> stream_Doc_node_class = doc_inst.getLstClass().stream();
            Stream<DOC_node_class> filter_Doc_node_class = stream_Doc_node_class.filter((e) -> e.getUid_owner() == dto_users.getId());
            List<DOC_node_class> lst_Doc_node_class = filter_Doc_node_class.collect(Collectors.toList());
            DTO_inst_class dto_inst_class = new DTO_inst_class();
            dto_inst_class.setUid(dto_users.getUid());
            for(DOC_node_class tmp_doc_node_class:lst_Doc_node_class)
            {
                DT_inst_class newItem = new DT_inst_class();
                newItem.setClassid(tmp_doc_node_class.getClassid());
                newItem.setName(tmp_doc_node_class.getName());
                dto_inst_class.getLstClass().add(newItem);
            }
            return Services_common.newCommonResItem(dto_inst_class, "8001", false,true);
        }
        catch (Exception err)
        {
            return Services_common.newCommonResStringItem(err.getMessage(), "4001", true,false);
        }
    }


    @GetMapping("/inst/class/{classid}")
    @ResponseBody
    public DTO_common GetClassDetail(@PathVariable(name="classid") String classid) {
        try {
            DTO_users dto_users = Services_common.getUserFromRedis();
            String inst_code = dto_users.getCode();
            DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
            DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
            Stream<DOC_node_class> stream_Doc_node_class = doc_inst.getLstClass().stream();
            Stream<DOC_node_class> filter_Doc_node_class = stream_Doc_node_class.filter((e) -> e.getClassid().equals(classid));
            List<DOC_node_class> lst_Doc_node_class = filter_Doc_node_class.collect(Collectors.toList());
            if(lst_Doc_node_class!=null && lst_Doc_node_class.size()>0) {
                OUTS_inst_classes_class outs_inst_classes_class = new OUTS_inst_classes_class();
                outs_inst_classes_class.setInst_code(inst_code);
                outs_inst_classes_class.setInst_name(doc_inst.getClass(classid).getName());
                OUTS_node_inst_classes_class newNodeItem = new OUTS_node_inst_classes_class();
                newNodeItem.setActive(lst_Doc_node_class.get(0).getIsgoing());
                newNodeItem.setTitle(lst_Doc_node_class.get(0).getName());
                newNodeItem.setOwner(services_users_basicinfo.GetUserName(dto_users.getId()));
                List<DOC_node_student> lstStudents = lst_Doc_node_class.get(0).getLstStudents();
                if(lstStudents!=null)
                {
                    newNodeItem.setCount_boys((int) lst_Doc_node_class.get(0).getLstStudents().stream().filter((e) -> e.getGender() == "1").count());
                    newNodeItem.setCount_girls((int) lst_Doc_node_class.get(0).getLstStudents().stream().filter((e) -> e.getGender() == "2").count());
                    outs_inst_classes_class.setOuts_node_inst_classes_class(newNodeItem);
                    outs_inst_classes_class.setLstStudents(lstStudents);
                }
                return Services_common.newCommonResItem(outs_inst_classes_class, "8001", false, true);
            }
            else
            {
                return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", false,false);
            }
        } catch (Exception err) {
            return Services_common.newCommonResStringItem(err.getMessage(), "4001", true,false);
        }
    }





    /*
    @PostMapping("/inst/class/{classid}/student/import")
    @ResponseBody
    public DTO_common setImport(@PathVariable(name="classid") String classid, @RequestBody DTI_inst_student_import_rmap dti_inst_student_import_rmap)
    {
        try {
            DTO_users dto_users = Services_common.getUserFromRedis();
            List<Map<String,String>> CVSResults = Services_common.GetCVSResultForName(Services_common.getTmpPoolPath()+"\\"+docServices_userpf.GetCurrentUploadFileName(dto_users.getUid()));
            for (Map<String,String> CVSRow:CVSResults)
            {

            }

        }
        catch (Exception err)
        {

        }
    }
    */





}
