package ikcoder.services.controller;

import ikcoder.entities.coredb_basic.DT.DT_inst;
import ikcoder.entities.coredb_basic.DTI.DTI_class;
import ikcoder.entities.coredb_basic.DTI.DTI_inst;
import ikcoder.entities.coredb_basic.DTO.DTO_common;
import ikcoder.entities.coredb_basic.DTO.DTO_inst_mapinfo;
import ikcoder.entities.coredb_basic.DTO.DTO_users;
import ikcoder.services.docs.documents.DOC_inst;
import ikcoder.services.docs.nodes.DOC_node_class;
import ikcoder.services.docs.nodes.DOC_node_student;
import ikcoder.services.docs.outs.documents.OUTS_inst_classes_matrix;
import ikcoder.services.docs.outs.nodes.OUTS_node_inst_classes_class;
import ikcoder.services.docservices.DocServices_inst;
import ikcoder.services.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class Controller_inst {

    Services_inst services_inst;
    Services_inst_mapinfo services_inst_mapinfo;
    Services_messages services_messages;
    DocServices_inst docServices_inst;
    StringRedisTemplate redisTemplate;
    Services_users_basicinfo services_users_basicinfo;

    @Autowired
    public Controller_inst(Services_inst services_inst, Services_inst_mapinfo services_inst_mapinfo, Services_messages services_messages, DocServices_inst docServices_inst, StringRedisTemplate redisTemplate, Services_users_basicinfo services_users_basicinfo) {
        this.services_inst = services_inst;
        this.services_inst_mapinfo = services_inst_mapinfo;
        this.services_messages = services_messages;
        this.docServices_inst = docServices_inst;
        this.redisTemplate = redisTemplate;
        this.services_users_basicinfo = services_users_basicinfo;
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
                    return Services_common.newCommonResItem(services_messages.GetMessage_code("8001"), "8001", false);
                } else {
                    return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true);
                }
            }
            else
                return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true);
        }
        else
        {
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true);
        }
    }

    @GetMapping("/inst/docs/new/{inst_code}")
    @ResponseBody
    public DTO_common NewInstDoc(@PathVariable String inst_code) {
        if(!services_inst_mapinfo.IsExisted(inst_code)) {
            UUID uuid = UUID.randomUUID();
            services_inst_mapinfo.NewItem(inst_code, uuid.toString());
            if (docServices_inst.NewInst(inst_code, uuid.toString())) {
                return Services_common.newCommonResItem(services_messages.GetMessage_code("8001"), "8001", false);
            } else {
                return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true);
            }
        }
        else
        {
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true);
        }
    }


    @PostMapping("/inst/class")
    @ResponseBody
    public DTO_common NewInstDoc(@RequestBody DTI_class dti_class) {
       DTO_users dto_users = Services_common.getUserFromRedis();
       Integer uid_owner = dto_users.getId();
        if(docServices_inst.NewClasses(dti_class.getDocid_inst(),dti_class.getName(),uid_owner,dti_class.getCurrent_term()))
            return Services_common.newCommonResItem(services_messages.GetMessage_code("8001"), "8001", false);
        else
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", true);
    }

    @GetMapping("/inst/class/student/number/auto/{classid}")
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
            Integer sizeValue = lst_Doc_node_class.get(0).getLst_students().size();
            return Services_common.newCommonResStringItem(sizeValue.toString(), "8001", false);
        }
        catch (Exception err)
        {
            return Services_common.newCommonResStringItem(err.getMessage(), "4001", true);
        }
    }


    @GetMapping("/inst/matrix")
    @ResponseBody
    public OUTS_inst_classes_matrix GetMatrix()
    {
        DTO_users dto_users = Services_common.getUserFromRedis();
        String inst_code = dto_users.getCode();
        DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
        DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
        DT_inst dt_inst = services_inst.SelectInst(inst_code);
        Stream<DOC_node_class> stream_Doc_node_class = doc_inst.getLstClass().stream();
        OUTS_inst_classes_matrix outs_inst_classes_matrix=new OUTS_inst_classes_matrix();
        outs_inst_classes_matrix.setInst_code(inst_code);
        outs_inst_classes_matrix.setInst_name(dt_inst.getName());
        outs_inst_classes_matrix.setUid(dto_users.getUid());
        List<DOC_node_class> lstClasses=stream_Doc_node_class.collect(Collectors.toList());
        for(DOC_node_class tmp_Doc_node_class : lstClasses) {
            OUTS_node_inst_classes_class newNodeItem = new OUTS_node_inst_classes_class();
            newNodeItem.setActive(tmp_Doc_node_class.getIsgoing());
            newNodeItem.setTitle(tmp_Doc_node_class.getName());
            newNodeItem.setOwner(services_users_basicinfo.GetUserName(dt_inst.getId()));
            newNodeItem.setCount_boys((int) tmp_Doc_node_class.getLstStudents().stream().filter((e) -> e.getGender() == "1").count());
            newNodeItem.setCount_girls((int) tmp_Doc_node_class.getLstStudents().stream().filter((e) -> e.getGender() == "2").count());
            List<OUTS_node_inst_classes_class> lstClasses;
            if (outs_inst_classes_matrix.getClasses_matrix().containsKey(tmp_Doc_node_class.getStartyear())) {
                lstClasses = outs_inst_classes_matrix.getClasses_matrix().get(tmp_Doc_node_class.getStartyear());
            } else {
                lstClasses = new ArrayList<>();
            }
            List<OUTS_node_inst_classes_class> lstClasses = outs_inst_classes_matrix.getClasses_matrix().get(tmp_Doc_node_class.getStartyear());
            lstClasses.add(newNodeItem);
            outs_inst_classes_matrix.getClasses_matrix().put(tmp_Doc_node_class.getStartyear().toString(), lstClasses);
        }
        return outs_inst_classes_matrix;
    }



}
