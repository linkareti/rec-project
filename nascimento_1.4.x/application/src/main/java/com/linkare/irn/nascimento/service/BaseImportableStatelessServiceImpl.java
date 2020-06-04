package com.linkare.irn.nascimento.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.dao.mapping.MapperDAO;
import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.model.ext.mapping.Importable;
import com.linkare.irn.nascimento.model.ext.mapping.MappingProcessResult;
import com.linkare.irn.nascimento.model.ext.mapping.organization.OrganizationDTO;
import com.linkare.irn.nascimento.model.mapping.Mapper;
import com.linkare.irn.nascimento.service.mapping.ApplicationCsvToBean;
import com.linkare.irn.nascimento.service.mapping.IMapperProcessor;
import com.linkare.irn.nascimento.service.mapping.exception.CSVFileParsingException;
import com.linkare.irn.nascimento.service.mapping.exception.EmailFieldParsingException;
import com.linkare.irn.nascimento.service.mapping.exception.ExistingEntityException;
import com.linkare.irn.nascimento.service.mapping.exception.MappingExistsException;
import com.linkare.irn.nascimento.util.CDIUtils;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public abstract class BaseImportableStatelessServiceImpl<ENTITY extends DomainObject, DAO extends GenericDAO<ENTITY>, DTO extends Importable<ENTITY>>
		extends BaseStatelessServiceImpl<ENTITY, DAO> implements IMapperProcessor<ENTITY, DTO, DAO> {

	private static final Logger LOG = LoggerFactory.getLogger(BaseImportableStatelessServiceImpl.class);

	private static final char COLUMN_SEPARATOR = ';';

	private MapperDAO mapperDAO;

	@PostConstruct
	public void init() {
		this.mapperDAO = new MapperDAO(getEntityManager());
	}

	@SuppressWarnings("unchecked")
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	@Override
	public MappingProcessResult load(InputStream in) {
		final MappingProcessResult result = new MappingProcessResult();
		final CsvToBean<DTO> csvToBean = new ApplicationCsvToBean<DTO>();

		int lineNumber = 1;

		try (final CSVReader reader = new CSVReader(new InputStreamReader(in), COLUMN_SEPARATOR)) {
			try {
				final List<DTO> entries = csvToBean.parse(createMappingStrategy(), reader);

				for (DTO entry : entries) {
					CDIUtils.getCdiBean(getClass()).processEntry(entry, result, lineNumber++);
				}
			} catch (Exception e) {
				LOG.error("Error processing file.", e);
				result.addError(0, e.getMessage());

				throw new CSVFileParsingException(e);
			}
		} catch (final IOException e) {
			throw new CSVFileParsingException(e);
		}

		return result;
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void processEntry(final DTO entry, final MappingProcessResult result, final int lineNumber) {
		try {
			boolean doubleMapping = false;

			if (entry != null) {
				// mapping already exists?
				Mapper mapper = getMapperDAO().findByModuleAndExternalId(entry.getModule(), entry.getExternalId());
				if (mapper != null) {
					throw new MappingExistsException(entry.getModule() + " with external ID " + entry.getExternalId()
							+ " has already been imported");
				} else if (entry instanceof OrganizationDTO) {

					OrganizationDTO organizationDTO = (OrganizationDTO) entry;

					if (!organizationDTO.hasValidName()) {
						throw new EmailFieldParsingException(
								entry.getModule() + " with name " + organizationDTO.getName() + " has an invalid name");
					}
				}

				try {

					ENTITY entity = getOrCreateEntity(entry);
					doAdditionalMappings(entry, entity);

					doValidation(entry, entity);
					entity = create(entity);

					entry.setInternalId(entity.getId());

				} catch (final ExistingEntityException e) {
					entry.setInternalId(e.getExistingId());

					result.addDuplicate(lineNumber, entry.getModule() + " with external ID " + entry.getExternalId()
							+ " was mapped again to an existing entity with internal ID " + entry.getInternalId());

					doubleMapping = true;
				}

				mapper = new Mapper();
				mapper.setInternalId(entry.getInternalId());
				mapper.setExternalId(entry.getExternalId());
				mapper.setModule(entry.getModule());

				getMapperDAO().create(mapper);

				if (!doubleMapping) {
					result.addSuccess(lineNumber, entry.getModule() + " with external ID " + entry.getExternalId()
							+ " was imported with internal ID " + entry.getInternalId());
				}
			} else {
				result.addSkip(lineNumber, "Parsed record is null");
			}
		} catch (final MappingExistsException e) {
			result.addSkip(lineNumber, e.getMessage());
		} catch (final ValidationException e) {
			if (e instanceof ConstraintViolationException) {
				final ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
				result.addError(lineNumber, StringUtils.join(
						toPrettyConstraintViolations(constraintViolationException.getConstraintViolations()), ","));
			} else {
				result.addError(lineNumber, e.getMessage());
			}
		}
	}

	private List<String> toPrettyConstraintViolations(final Set<ConstraintViolation<?>> violations) {
		final List<String> result = new ArrayList<>(violations.size());
		for (final ConstraintViolation<?> violation : violations) {
			result.add((violation.getPropertyPath() == null ? "" : violation.getPropertyPath().toString()) + ": "
					+ violation.getMessage());
		}
		return result;
	}

	@Override
	public void doValidation(DTO entry, ENTITY entity) {
		validate(entity);
	}

	/**
	 * @return the mapperDAO
	 */
	protected MapperDAO getMapperDAO() {
		return mapperDAO;
	}
}