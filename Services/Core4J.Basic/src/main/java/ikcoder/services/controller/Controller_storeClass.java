package ikcoder.services.controller;

import ikcoder.services.docs.documents.DOC_inst;
import ikcoder.services.docs.nodes.DOC_node_class;
import ikcoder.services.docs.nodes.DOC_node_student;
import ikcoder.services.docs.outs.documents.OUTS_inst_classes_class;
import ikcoder.services.docs.outs.nodes.OUTS_node_inst_classes_class;
import ikcoder.services.docservices.DocServices_inst;
import ikcoder.services.docservices.DocServices_userpf;
import ikcoder.services.entities.DTI.DTI_class;
import ikcoder.services.entities.DTO.DTO_common;
import ikcoder.services.entities.DTO.DTO_inst_mapinfo;
import ikcoder.services.entities.DTO.DTO_users;
import ikcoder.services.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class Controller_storeClass {

    Services_inst_mapinfo services_inst_mapinfo;
    Services_messages services_messages;
    DocServices_inst docServices_inst;
    Services_users_basicinfo services_users_basicinfo;
    Services_inst services_inst;

    @Autowired
    public Controller_storeClass(Services_inst_mapinfo services_inst_mapinfo, Services_messages services_messages, DocServices_inst docServices_inst, Services_users_basicinfo services_users_basicinfo, Services_inst services_inst) {
        this.services_inst_mapinfo = services_inst_mapinfo;
        this.services_messages = services_messages;
        this.docServices_inst = docServices_inst;
        this.services_users_basicinfo = services_users_basicinfo;
        this.services_inst = services_inst;
    }

    @PostMapping("/store/class/new")
    @ResponseBody
    public DTO_common NewClass(@RequestBody DTI_class dti_class) {
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


    @GetMapping("/store/class/list")
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

    @GetMapping("/store/class/status/{classid}")
    @ResponseBody
    public DTO_common GetClassStatus(@PathVariable(name = "classid") String classid)
    {
        try {
            DTO_users dto_users = Services_common.getUserFromRedis();
            String inst_code = dto_users.getCode();
            DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
            DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
            boolean statusResult;
            if(doc_inst.getClass(classid)!=null)
                statusResult = true;
            else
                statusResult = false;
            if(statusResult)
                return Services_common.newCommonResStringItem("true", "8001", false,true);
            else
                return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true,false);
        }
        catch (Exception err)
        {
            return Services_common.newCommonResStringItem(err.getMessage(), "4001", true,false);
        }
    }


    @GetMapping("/store/class/select/{classid}")
    @ResponseBody
    public DTO_common GetClassDetail(@PathVariable(name = "classid") String classid) {
        try {
            DTO_users dto_users = Services_common.getUserFromRedis();
            String inst_code = dto_users.getCode();
            DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
            DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
            Stream<DOC_node_class> stream_Doc_node_class = doc_inst.getLstClass().stream();
            Stream<DOC_node_class> filter_Doc_node_class = stream_Doc_node_class.filter((e) -> e.getClassid().equals(classid));
            List<DOC_node_class> lst_Doc_node_class = filter_Doc_node_class.collect(Collectors.toList());
            if (lst_Doc_node_class != null && lst_Doc_node_class.size() > 0) {
                OUTS_inst_classes_class outs_inst_classes_class = new OUTS_inst_classes_class();
                outs_inst_classes_class.setInst_code(inst_code);
                outs_inst_classes_class.setInst_name(doc_inst.getClass(classid).getName());
                OUTS_node_inst_classes_class newNodeItem = new OUTS_node_inst_classes_class();
                newNodeItem.setActive(lst_Doc_node_class.get(0).getIsgoing());
                newNodeItem.setTitle(lst_Doc_node_class.get(0).getName());
                newNodeItem.setOwner(services_users_basicinfo.GetUserName(dto_users.getId()));
                List<DOC_node_student> lstStudents = lst_Doc_node_class.get(0).getLstStudents();
                if (lstStudents != null) {
                    newNodeItem.setCount_boys((int) lst_Doc_node_class.get(0).getLstStudents().stream().filter((e) -> e.getGender() == "1").count());
                    newNodeItem.setCount_girls((int) lst_Doc_node_class.get(0).getLstStudents().stream().filter((e) -> e.getGender() == "2").count());
                    outs_inst_classes_class.setOuts_node_inst_classes_class(newNodeItem);
                    outs_inst_classes_class.setLstStudents(lstStudents);
                }
                return Services_common.newCommonResItem(outs_inst_classes_class, "8001", false, true);
            } else {
                return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", false, false);
            }
        } catch (Exception err) {
            return Services_common.newCommonResStringItem(err.getMessage(), "4001", true, false);
        }
    }

    @GetMapping("/store/class/remove/{classid}")
    @ResponseBody
    public DTO_common SetRemove(@PathVariable(name = "classid") String classid) {
        DTO_users dto_users = Services_common.getUserFromRedis();
        String inst_code = dto_users.getCode();
        DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
        DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
        if(docServices_inst.RemoveClass(dto_inst_mapinfo.getDocid_basic(),classid))
            return Services_common.newCommonResItem(services_messages.GetMessage_code("8001"), "8001", false,true);
        else
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true,false);
    }

}
