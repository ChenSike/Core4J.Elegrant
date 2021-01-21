package ikcoder.services.services;

import ikcoder.entities.coredb_basic.DTI.DTI_term;
import ikcoder.entities.coredb_basic.DTO.DTO_term;
import ikcoder.services.mappers.Mapper_term;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.Literal;

import java.sql.SQLTransactionRollbackException;
import java.util.ArrayList;
import java.util.List;

public class Services_term {

    Mapper_term mapper_term;

    @Autowired
    public Services_term(Mapper_term mapper_term) {
        this.mapper_term = mapper_term;
    }

    public void NewTermItem(String start_year,String start_month,String end_year,String end_month,String comment,String inst_code)
    {
        DTI_term dti_term=new DTI_term();
        dti_term.setStart_year(start_year);
        dti_term.setStart_month(start_month);
        dti_term.setEnd_year(end_year);
        dti_term.setEnd_month(end_month);
        dti_term.setComment(comment);
        dti_term.setInst_code(inst_code);
        mapper_term.Insert_NewItem(dti_term);
    }

    public List<String> SelectStartYears(String inst_code)
    {
        List<DTO_term> lstResult = mapper_term.Select_InstCode(inst_code);
        List<String> result=new ArrayList<>();
        for(DTO_term tmpItem:lstResult)
        {
            result.add(tmpItem.getStart_year());
        }
        return result;
    }

}
