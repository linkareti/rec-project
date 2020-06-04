package com.linkare.irn.nascimento.web.controller.shared.core;

import static com.linkare.irn.nascimento.util.ApplicationMessageUtils.getMessage;

import java.io.ByteArrayInputStream;
import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ComponentSystemEvent;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.primefaces.component.fileupload.FileUpload;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;
import org.primefaces.model.UploadedFile;

import com.linkare.irn.nascimento.model.core.BirthRegistrationBabyPhoto;
import com.linkare.irn.nascimento.model.core.DocumentType;
import com.linkare.irn.nascimento.service.core.BirthRegistrationService;
import com.linkare.irn.nascimento.util.JsfUtils;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class DocumentHelper<T extends BirthRegistrationControllerAware> implements Serializable {

	private static final long serialVersionUID = 864290724116936837L;

	private boolean isAttachmentsRequired;

	private T controller;

	private boolean fileInitialized;

	private StreamedContent file;

	private StreamedContent babyPhoto;

	private DocumentType fileType;

	private BirthRegistrationService birthRegistrationService;

	public DocumentHelper(boolean isAttachmentsRequired, final T controller,
			final BirthRegistrationService birthRegistrationService) {
		super();
		this.isAttachmentsRequired = isAttachmentsRequired;
		this.controller = controller;
		this.birthRegistrationService = birthRegistrationService;
	}

	public void clearUploadedDocument(String type) {
		DocumentType documentType = DocumentType.valueOf(type);

		if (documentType.isBirthProof()) {
			clearBirthProofUploadedDocument();
		} else {
			clearBabyPhotoUploadedDocument();
		}
	}

	private void clearBabyPhotoUploadedDocument() {

		if (controller.getBirthRegistration().getBabyPhoto() != null) {
			controller.getBirthRegistration().getBabyPhoto().setFilename(null);
			controller.getBirthRegistration().getBabyPhoto().setFileContentType(null);
			controller.getBirthRegistration().getBabyPhoto().setFileSize(null);
			controller.getBirthRegistration().getBabyPhoto().setFileContent(null);
		}
	}

	private void clearBirthProofUploadedDocument() {
		controller.getBirthRegistration().setFilename(null);
		controller.getBirthRegistration().setFileContentType(null);
		controller.getBirthRegistration().setFileSize(null);
		controller.getBirthRegistration().setFileContent(null);
	}

	public String getBabyPhotoFileName() {
		BirthRegistrationBabyPhoto babyPhoto = controller.getBirthRegistration().getBabyPhoto();
		return (babyPhoto != null) ? babyPhoto.getFilename() : null;
	}

	public String getUploadedFileName() {
		return controller.getBirthRegistration().getFilename();
	}

	public String getBabyPhotoFileIcon() {

		BirthRegistrationBabyPhoto babyPhoto = controller.getBirthRegistration().getBabyPhoto();

		return (babyPhoto != null) ? controller.getBirthRegistration().getFileIcon(babyPhoto.getFileContentType())
				: null;
	}

	public String getUploadedFileIcon() {
		return controller.getBirthRegistration().getFileIcon(controller.getBirthRegistration().getFileContentType());
	}

	public void handleFileUpload(final FileUploadEvent event) {

		UploadedFile uploadedFile = event.getFile();
		Object type = event.getComponent().getAttributes().get("type");

		if (type != null) {
			DocumentType documentType = DocumentType.valueOf(type.toString());

			if (uploadedFile != null) {

				String fileNameTrimmed = org.apache.commons.lang3.StringUtils.stripAccents(uploadedFile.getFileName());

				if (documentType.isBirthProof()) {
					handleBirhProofUpload(uploadedFile, fileNameTrimmed);
				} else {
					handleBabyPhotoUpload(uploadedFile, fileNameTrimmed);
				}
			}
		}
	}

	private void handleBabyPhotoUpload(final UploadedFile uploadedFile, final String fileName) {
		controller.getBirthRegistration().getBabyPhoto().setFilename(FilenameUtils.getName(fileName));
		controller.getBirthRegistration().getBabyPhoto().setFileContentType(uploadedFile.getContentType());
		controller.getBirthRegistration().getBabyPhoto().setFileSize(uploadedFile.getSize());
		controller.getBirthRegistration().getBabyPhoto().setFileContent(uploadedFile.getContents());
	}

	private void handleBirhProofUpload(final UploadedFile uploadedFile, final String fileName) {
		controller.getBirthRegistration().setFilename(FilenameUtils.getName(fileName));
		controller.getBirthRegistration().setFileContentType(uploadedFile.getContentType());
		controller.getBirthRegistration().setFileSize(uploadedFile.getSize());
		controller.getBirthRegistration().setFileContent(uploadedFile.getContents());
	}

	public void validateUploadedDocuments(final ComponentSystemEvent event) {
		final UIComponent panelGroup = event.getComponent();
		final FileUpload fileUpload = getFileUploadComponent(panelGroup);

		if (!isValidationDisabled()) {
			if (isAttachmentsRequired && StringUtils.isBlank(controller.getBirthRegistration().getFilename())) {
				JsfUtils.addMessage(FacesMessage.SEVERITY_ERROR, fileUpload.getClientId(), getMessage("message.error"),
						getMessage("message.error.uploadedFileRequired"));
				fileUpload.setValid(false);
				FacesContext.getCurrentInstance().renderResponse();
				return;
			}
		}
	}

	private FileUpload getFileUploadComponent(final UIComponent panelGroup) {
		for (final UIComponent child : panelGroup.getChildren()) {
			if (child instanceof FileUpload) {
				return (FileUpload) child;
			}
		}
		return null;
	}

	private boolean isValidationDisabled() {
		final HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		final String disableValidationParameter = request.getParameter("disableValidation");
		return StringUtils.isNotBlank(disableValidationParameter) && Boolean.parseBoolean(disableValidationParameter);
	}

	/**
	 * @return the isAttachmentsRequired
	 */
	public boolean isAttachmentsRequired() {
		return isAttachmentsRequired;
	}

	/**
	 * @param isAttachmentsRequired the isAttachmentsRequired to set
	 */
	public void setAttachmentsRequired(boolean isAttachmentsRequired) {
		this.isAttachmentsRequired = isAttachmentsRequired;
	}

	public StreamedContent getFile() {
		if (!fileInitialized || !fileType.isBirthProof()) {
			fileInitialized = true;
			if (controller.getBirthRegistration().isPersistent()) {
				if (StringUtils.isNotBlank(controller.getBirthRegistration().getFilename())) {
					fileType = DocumentType.BIRTH_PROOF;
					this.file = new DefaultStreamedContent(
							new ByteArrayInputStream(birthRegistrationService.getFileContent(fileType,
									controller.getBirthRegistration())),
							controller.getBirthRegistration().getFileContentType(),
							controller.getBirthRegistration().getFilename());
				}
			} else {
				if (StringUtils.isNotBlank(controller.getBirthRegistration().getFilename())) {
					this.file = new DefaultStreamedContent(
							new ByteArrayInputStream(controller.getBirthRegistration().getFileContent()),
							controller.getBirthRegistration().getFileContentType(),
							controller.getBirthRegistration().getFilename());
				}
			}
		}

		return file;
	}

	public StreamedContent getBabyPhoto() {

		if (!fileInitialized || !fileType.isBabyPhoto()) {
			fileInitialized = true;
			if (controller.getBirthRegistration().isPersistent()) {
				if (StringUtils.isNotBlank(controller.getBirthRegistration().getBabyPhoto().getFilename())) {
					fileType = DocumentType.BABY_PHOTO;
					this.babyPhoto = new DefaultStreamedContent(
							new ByteArrayInputStream(birthRegistrationService.getFileContent(fileType,
									controller.getBirthRegistration())),
							controller.getBirthRegistration().getBabyPhoto().getFileContentType(),
							controller.getBirthRegistration().getBabyPhoto().getFilename());
				}
			} else {
				if (StringUtils.isNotBlank(controller.getBirthRegistration().getBabyPhoto().getFilename())) {
					this.babyPhoto = new DefaultStreamedContent(
							new ByteArrayInputStream(controller.getBirthRegistration().getBabyPhoto().getFileContent()),
							controller.getBirthRegistration().getBabyPhoto().getFileContentType(),
							controller.getBirthRegistration().getBabyPhoto().getFilename());
				}
			}
		}
		return babyPhoto;
	}

	/**
	 * @return the fileInitialized
	 */
	public boolean isFileInitialized() {
		return fileInitialized;
	}

	/**
	 * @param fileInitialized the fileInitialized to set
	 */
	public void setFileInitialized(boolean fileInitialized) {
		this.fileInitialized = fileInitialized;
	}
}