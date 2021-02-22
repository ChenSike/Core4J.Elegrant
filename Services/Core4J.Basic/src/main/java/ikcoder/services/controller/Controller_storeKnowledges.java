package ikcoder.services.controller;

import ikcoder.services.docs.documents.DOC_inst;
import ikcoder.services.docs.documents.DOC_knowledges;
import ikcoder.services.docs.nodes.DOC_node_knowlege;
import ikcoder.services.docservices.DocServices_inst;
import ikcoder.services.docservices.DocServices_knowledge;
import ikcoder.services.entities.DTI.DTI_class;
import ikcoder.services.entities.DTI.DTI_store_knowledges;
import ikcoder.services.entities.DTO.DTO_common;
import ikcoder.services.entities.DTO.DTO_inst_mapinfo;
import ikcoder.services.entities.DTO.DTO_users;
import ikcoder.services.services.Services_common;
import ikcoder.services.services.Services_inst_mapinfo;
import ikcoder.services.services.Services_messages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;
import java.util.Map;

@RestController
public class Controller_storeKnowledges {

    DocServices_knowledge docServices_knowledge;
    Services_inst_mapinfo services_inst_mapinfo;
    Services_messages services_messages;
    DocServices_inst docServices_inst;

    @Autowired
    public Controller_storeKnowledges(DocServices_knowledge docServices_knowledge, Services_inst_mapinfo services_inst_mapinfo, Services_messages services_messages, DocServices_inst docServices_inst) {
        this.docServices_knowledge = docServices_knowledge;
        this.services_inst_mapinfo = services_inst_mapinfo;
        this.services_messages = services_messages;
        this.docServices_inst = docServices_inst;
    }


    @GetMapping("/store/knowledges/list")
    @ResponseBody
    public Map<String, DOC_node_knowlege> getKnowledgesList(@RequestBody DTI_store_knowledges dti_store_knowledges) {
        DTO_users dto_users = Services_common.getUserFromRedis();
        String inst_code = dto_users.getCode();
        DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
        DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
        if (!docServices_knowledge.isDocExisted(doc_inst.getDocid_inst(), dti_store_knowledges.getSubject(), dti_store_knowledges.getGrade()))
            docServices_knowledge.newKnowledgesDoc(doc_inst.getDocid_inst(), dti_store_knowledges.getSubject(), dti_store_knowledges.getGrade());
        Map<String, DOC_node_knowlege> result = docServices_knowledge.listNodes(doc_inst.getDocid_inst(), dti_store_knowledges.getSubject(), dti_store_knowledges.getGrade());
        return result;
    }

    @GetMapping("/store/knowledges/connections")
    @ResponseBody
    public List<String> getConnections(@RequestBody DTI_store_knowledges dti_store_knowledges)
    {
        List<String> lstResult=new ArrayList<>();
        DTO_users dto_users = Services_common.getUserFromRedis();
        String inst_code = dto_users.getCode();
        DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
        DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
        DOC_knowledges doc_knowledges = docServices_knowledge.selectKnowledgeDoc(doc_inst.getDocid_inst(),dti_store_knowledges.getSubject(),dti_store_knowledges.getGrade());
        DOC_node_knowlege doc_node_knowlege = docServices_knowledge.selectKnowledgeNode(doc_knowledges,dti_store_knowledges.getTitle());
        lstResult = doc_node_knowlege.getConnection();
        return lstResult;
    }

    @GetMapping("/store/knowledges/update")
    @ResponseBody
    public DTO_common updateKnowledges(@RequestBody DTI_store_knowledges dti_store_knowledges) {
        try {
            DTO_users dto_users = Services_common.getUserFromRedis();
            String inst_code = dto_users.getCode();
            DTO_inst_mapinfo dto_inst_mapinfo = services_inst_mapinfo.SelectItemByCode(inst_code);
            DOC_inst doc_inst = docServices_inst.GetInstDocument(dto_inst_mapinfo.getDocid_basic());
            if (!docServices_knowledge.isDocExisted(doc_inst.getDocid_inst(), dti_store_knowledges.getSubject(), dti_store_knowledges.getGrade()))
                docServices_knowledge.newKnowledgesDoc(doc_inst.getDocid_inst(), dti_store_knowledges.getSubject(), dti_store_knowledges.getGrade());
            DOC_knowledges doc_knowledges = docServices_knowledge.selectKnowledgeDoc(doc_inst.getDocid_inst(), dti_store_knowledges.getSubject(), dti_store_knowledges.getGrade());
            DOC_node_knowlege doc_node_knowlege = docServices_knowledge.createKnowledgeNode(doc_knowledges, dti_store_knowledges.getTitle(), dti_store_knowledges.getSubject(), dti_store_knowledges.getConnections());
            return Services_common.newCommonResItem(services_messages.GetMessage_code("8001"), "8001", false, true);
        } catch (Exception err) {
            return Services_common.newCommonResItem(services_messages.GetMessage_code("4001"), "4001", false, false);
        }
    }
}