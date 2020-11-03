/*
 * Created on 7 Oct 2017 ( Time 18:41:59 )
 * Generated by Telosys Tools Generator ( version 2.1.1 )
 */
package com.gvpt.admintool.bean.jpa;
import java.io.Serializable;

import javax.persistence.*;

/**
 * Composite primary key for entity "CodeConfigEntity" ( stored in table "GVPT_CODE_CONFIG" )
 *
 * @author Telosys Tools Generator
 *
 */
 @Embeddable
public class CodeConfigEntityKey implements Serializable {

    private static final long serialVersionUID = 1L;

    //----------------------------------------------------------------------
    // ENTITY KEY ATTRIBUTES 
    //----------------------------------------------------------------------
    @Column(name="CODE_CONFIG_ID", nullable=false)
    private Long       codeConfigId ;
    
    @Column(name="CODE_TYPE", nullable=false, length=500)
    private String     codeType     ;
    
    @Column(name="CODE_NAME", nullable=false, length=100)
    private String     codeName     ;
    

    //----------------------------------------------------------------------
    // CONSTRUCTORS
    //----------------------------------------------------------------------
    public CodeConfigEntityKey() {
        super();
    }

    public CodeConfigEntityKey( Long codeConfigId, String codeType, String codeName ) {
        super();
        this.codeConfigId = codeConfigId ;
        this.codeType = codeType ;
        this.codeName = codeName ;
    }
    
    //----------------------------------------------------------------------
    // GETTERS & SETTERS FOR KEY FIELDS
    //----------------------------------------------------------------------
    public void setCodeConfigId( Long value ) {
        this.codeConfigId = value;
    }
    public Long getCodeConfigId() {
        return this.codeConfigId;
    }

    public void setCodeType( String value ) {
        this.codeType = value;
    }
    public String getCodeType() {
        return this.codeType;
    }

    public void setCodeName( String value ) {
        this.codeName = value;
    }
    public String getCodeName() {
        return this.codeName;
    }


    //----------------------------------------------------------------------
    // equals METHOD
    //----------------------------------------------------------------------
	public boolean equals(Object obj) { 
		if ( this == obj ) return true ; 
		if ( obj == null ) return false ;
		if ( this.getClass() != obj.getClass() ) return false ; 
		CodeConfigEntityKey other = (CodeConfigEntityKey) obj; 
		//--- Attribute codeConfigId
		if ( codeConfigId == null ) { 
			if ( other.codeConfigId != null ) 
				return false ; 
		} else if ( ! codeConfigId.equals(other.codeConfigId) ) 
			return false ; 
		//--- Attribute codeType
		if ( codeType == null ) { 
			if ( other.codeType != null ) 
				return false ; 
		} else if ( ! codeType.equals(other.codeType) ) 
			return false ; 
		//--- Attribute codeName
		if ( codeName == null ) { 
			if ( other.codeName != null ) 
				return false ; 
		} else if ( ! codeName.equals(other.codeName) ) 
			return false ; 
		return true; 
	} 


    //----------------------------------------------------------------------
    // hashCode METHOD
    //----------------------------------------------------------------------
	public int hashCode() { 
		final int prime = 31; 
		int result = 1; 
		
		//--- Attribute codeConfigId
		result = prime * result + ((codeConfigId == null) ? 0 : codeConfigId.hashCode() ) ; 
		//--- Attribute codeType
		result = prime * result + ((codeType == null) ? 0 : codeType.hashCode() ) ; 
		//--- Attribute codeName
		result = prime * result + ((codeName == null) ? 0 : codeName.hashCode() ) ; 
		
		return result; 
	} 


    //----------------------------------------------------------------------
    // toString METHOD
    //----------------------------------------------------------------------
    public String toString() {
		StringBuffer sb = new StringBuffer(); 
		sb.append(codeConfigId); 
		sb.append("|"); 
		sb.append(codeType); 
		sb.append("|"); 
		sb.append(codeName); 
        return sb.toString();
    }
}