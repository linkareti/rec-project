package com.linkare.irn.nascimento.model.core;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.bytecode.internal.javassist.FieldHandled;
import org.hibernate.bytecode.internal.javassist.FieldHandler;

import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.model.validation.BirthRegistrationRegistrationValidation;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
@Entity
@Table(name = "birth_registration_document")
public class BirthRegistrationDocument extends DomainObject  {

    private static final long serialVersionUID = -5177032826129307329L;

    
    
    @Column(name = "filename")
    @NotNull(groups = BirthRegistrationRegistrationValidation.class)
    @Size(max = 150)
    private String filename;

    @Column(name = "file_content_type")
    @NotNull(groups = BirthRegistrationRegistrationValidation.class)
    @Size(max = 75)
    private String fileContentType;

    @Column(name = "file_size")
    @NotNull(groups = BirthRegistrationRegistrationValidation.class)
    private Long fileSize;

    @Column(name = "file_content", columnDefinition = "mediumblob")
    @Basic(fetch = FetchType.LAZY)
    @Lob
    @NotNull(groups = BirthRegistrationRegistrationValidation.class)
    private byte[] fileContent;

    /**
     * @return the filename
     */
    public String getFilename() {
	return filename;
    }

    /**
     * @param filename
     *            the filename to set
     */
    public void setFilename(String filename) {
	this.filename = filename;
    }

    /**
     * @return the fileContentType
     */
    public String getFileContentType() {
	return fileContentType;
    }

    /**
     * @param fileContentType
     *            the fileContentType to set
     */
    public void setFileContentType(String fileContentType) {
	this.fileContentType = fileContentType;
    }

    /**
     * @return the fileSize
     */
    public Long getFileSize() {
	return fileSize;
    }

    /**
     * @param fileSize
     *            the fileSize to set
     */
    public void setFileSize(Long fileSize) {
	this.fileSize = fileSize;
    }


      

    public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
        this.fileContent = fileContent;
    }


    
    
}