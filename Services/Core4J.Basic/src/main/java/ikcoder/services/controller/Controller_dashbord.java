package ikcoder.services.controller;

import ikcoder.services.docs.documents.DOC_inst;
import ikcoder.services.docs.nodes.DOC_node_class;
import ikcoder.services.docs.nodes.DOC_node_student;
import ikcoder.services.docs.outs.documents.OUTS_inst_classes_matrix;
import ikcoder.services.docs.outs.nodes.OUTS_node_inst_classes_class;
import ikcoder.services.docservices.DocServices_inst;
import ikcoder.services.entities.DT.DT_inst;
import ikcoder.services.entities.DTO.DTO_inst_mapinfo;
import ikcoder.services.entities.DTO.DTO_users;
import ikcoder.services.services.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
public class Controller_dashbord {

    Services_inst services_inst;
    Services_inst_mapinfo services_inst_mapinfo;
    DocServices_inst docServices_inst;
    Services_users_basicinfo services_users_basicinfo;

    public Controller_dashbord(Services_inst services_inst, Services_inst_mapinfo services_inst_mapinfo, DocServices_inst docServices_inst, Services_users_basicinfo services_users_basicinfo) {
        this.services_inst = services_inst;
        this.services_inst_mapinfo = services_inst_mapinfo;
        this.docServices_inst = docServices_inst;
        this.services_users_basicinfo = services_users_basicinfo;
    }

    @GetMapping("/dashbord/matrix")
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
        outs_inst_classes_matrix.setNow(Services_common.getCurrentDate());
        outs_inst_classes_matrix.setInst_code(inst_code);
        outs_inst_classes_matrix.setInst_name(dt_inst.getName());
        outs_inst_classes_matrix.setUid(dto_users.getUid());
        List<DOC_node_class> lstClasses=stream_Doc_node_class.collect(Collectors.toList());
        for(DOC_node_class tmp_Doc_node_class : lstClasses) {
            OUTS_node_inst_classes_class newNodeItem = new OUTS_node_inst_classes_class();
            newNodeItem.setActive(tmp_Doc_node_class.getIsgoing());
            newNodeItem.setTitle(tmp_Doc_node_class.getName());
            newNodeItem.setOwner(services_users_basicinfo.GetUserName(tmp_Doc_node_class.getUid_owner()));
            List<DOC_node_student> lstStudents=tmp_Doc_node_class.getLstStudents();
            if(lstStudents!=null) {
                newNodeItem.setCount_boys((int) lstStudents.stream().filter((e) -> e.getGender() == "1").count());
                newNodeItem.setCount_girls((int) lstStudents.stream().filter((e) -> e.getGender() == "2").count());
            }
            else
            {
                newNodeItem.setCount_boys(0);
                newNodeItem.setCount_girls(0);
            }
            List<OUTS_node_inst_classes_class> tmp_lstClasses;
            if (outs_inst_classes_matrix.getClasses_matrix().containsKey(tmp_Doc_node_class.getStartyear())) {
                tmp_lstClasses = outs_inst_classes_matrix.getClasses_matrix().get(tmp_Doc_node_class.getStartyear());
            } else {
                tmp_lstClasses = new ArrayList<>();
                List<OUTS_node_inst_classes_class> new_lstClasses = new ArrayList<>();
                outs_inst_classes_matrix.getClasses_matrix().put(tmp_Doc_node_class.getStartyear(), new_lstClasses);
            }
            tmp_lstClasses.add(newNodeItem);
            outs_inst_classes_matrix.getClasses_matrix().put(tmp_Doc_node_class.getStartyear().toString(), tmp_lstClasses);
        }
        return outs_inst_classes_matrix;
    }

}
