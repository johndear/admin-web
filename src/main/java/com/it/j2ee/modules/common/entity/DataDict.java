package com.it.j2ee.modules.common.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * DataDict entity. 
 * @author 
 */
@Entity
@Table(name="TC_DATA_DICT",schema="ydscm_common")
public class DataDict  implements java.io.Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8134572587627684446L;
	// Fields   
     private Integer dictId;
     private String dictType;
     private String dictCode;
     private String dictName;
     private Integer dictDisplayOrder;
     private Integer status;
     private String dictMemo;

    // Constructors
    /** default constructor */
    public DataDict() {
    }

	/** minimal constructor */
    public DataDict(Integer dictId, String dictType, String dictCode, String dictName, Integer status) {
        this.dictId = dictId;
        this.dictType = dictType;
        this.dictCode = dictCode;
        this.dictName = dictName;
        this.status = status;
    }
    
    /** full constructor */
    public DataDict(Integer dictId, String dictType, String dictCode, String dictName, Integer dictDisplayOrder, Integer status, String dictMemo) {
        this.dictId = dictId;
        this.dictType = dictType;
        this.dictCode = dictCode;
        this.dictName = dictName;
        this.dictDisplayOrder = dictDisplayOrder;
        this.status = status;
        this.dictMemo = dictMemo;
    }

    // Property accessors
    @Id  
    @Column(name="DICT_ID", unique=true, nullable=false)
    public Integer getDictId() {
        return this.dictId;
    }
    
    public void setDictId(Integer dictId) {
        this.dictId = dictId;
    }
    
    public String getDictType() {
        return this.dictType;
    }
    
    public void setDictType(String dictType) {
        this.dictType = dictType;
    }
    
    public String getDictCode() {
        return this.dictCode;
    }
    
    public void setDictCode(String dictCode) {
        this.dictCode = dictCode;
    }
    
    public String getDictName() {
        return this.dictName;
    }
    
    public void setDictName(String dictName) {
        this.dictName = dictName;
    }
    
    public Integer getDictDisplayOrder() {
        return this.dictDisplayOrder;
    }
    
    public void setDictDisplayOrder(Integer dictDisplayOrder) {
        this.dictDisplayOrder = dictDisplayOrder;
    }
    
    public Integer getStatus() {
        return this.status;
    }
    
    public void setStatus(Integer status) {
        this.status = status;
    }
    

    public String getDictMemo() {
        return this.dictMemo;
    }
    
    public void setDictMemo(String dictMemo) {
        this.dictMemo = dictMemo;
    }
    
    @Override
    public String toString() {
    	// TODO Auto-generated method stub
    	return "dictName="+ dictName +";dictCode="+ dictCode;
    }
   
}