package ikcoder.services.docs.documents;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("userpf")
public class DOC_userpf extends DOC_base {

    String docid_userpf;
    Integer uid;
    String tmppool_currentUploadFile;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getTmppool_currentUploadFile() {
        return tmppool_currentUploadFile;
    }

    public void setTmppool_currentUploadFile(String tmppool_currentUploadFile) {
        this.tmppool_currentUploadFile = tmppool_currentUploadFile;
    }

    public String getDocid_userpf() {
        return docid_userpf;
    }

    public void setDocid_userpf(String docid_userpf) {
        this.docid_userpf = docid_userpf;
    }
}
