package com.linkare.irn.nascimento.service.mapping;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.apache.commons.lang3.StringUtils;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.dao.mapping.MapperDAO;
import com.linkare.irn.nascimento.model.DomainException;
import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.model.ext.mapping.Importable;
import com.linkare.irn.nascimento.model.ext.mapping.MappingProcessResult;
import com.linkare.irn.nascimento.model.mapping.Mapper;
import com.linkare.irn.nascimento.service.mapping.exception.ExistingEntityException;
import com.linkare.irn.nascimento.service.mapping.exception.ExistingMappingException;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.bean.CsvToBean;
import au.com.bytecode.opencsv.bean.MappingStrategy;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class MapperProcessingHelper {

    private MapperProcessingHelper() {
    }

    public static <T extends DomainObject, E extends Importable<T>, DAO extends GenericDAO<T>> MappingProcessResult process(final MapperDAO mapperDAO,
	    final IMapperProcessor<T, E, DAO> processor, final InputStream in) throws IOException {
	final MappingProcessResult result = new MappingProcessResult();
	int lineNumber = 1;
	final CsvToBean<E> csvToBean = new ApplicationCsvToBean<E>();
	try (final CSVReader reader = new CSVReader(new InputStreamReader(in), ';', '"');) {
	    final List<E> entries = csvToBean.parse(createMappingStrategy(processor), reader);

	    for (E entry : entries) {
		try {
		    boolean doubleMapping = false;
		    if (entry != null) {
			checkIfAlreadyExists(mapperDAO, entry);

			try {
			    T entity = processor.getOrCreateEntity(entry);
			    processor.doAdditionalMappings(entry, entity);

			    processor.doValidation(entry, entity);
			    entity = processor.create(entity);

			    entry.setInternalId(entity.getId());
			} catch (final ExistingEntityException e) {
			    entry.setInternalId(e.getExistingId());
			    result.addDuplicate(lineNumber, entry.getModule() + " with external ID " + entry.getExternalId()
				    + " was mapped again to an existing entity with internal ID " + entry.getInternalId());
			    doubleMapping = true;
			}
			mapEntity(mapperDAO, entry);
		    }
		    if (!doubleMapping) {
			result.addSuccess(lineNumber, entry.getModule() + " with external ID " + entry.getExternalId() + " was imported with internal ID "
				+ entry.getInternalId());
		    }
		} catch (final ExistingMappingException e) {
		    result.addSkip(lineNumber, e.getMessage());
		} catch (final ValidationException e) {
		    if (e instanceof ConstraintViolationException) {
			final ConstraintViolationException constraintViolationException = (ConstraintViolationException) e;
			result.addError(lineNumber, StringUtils.join(constraintViolationException.getConstraintViolations(), ","));
		    } else {
			result.addError(lineNumber, e.getMessage());
		    }
		} catch (DomainException e) {
		    result.addError(lineNumber, e.getErrorCode());
		}
		lineNumber++;
	    }
	}
	return result;

    }

    private static <E extends Importable<?>> void checkIfAlreadyExists(final MapperDAO mapperDAO, E entry) {
	final Mapper existing = mapperDAO.findByModuleAndExternalId(entry.getModule(), entry.getExternalId());
	if (existing != null) {
	    throw new ExistingMappingException(entry.getModule() + " with external ID " + entry.getExternalId() + " has already been imported");
	}
    }

    private static <E extends Importable<?>> void mapEntity(final MapperDAO mapperDAO, final E entity) throws DomainException {
	final Mapper mapper = new Mapper();
	mapper.setInternalId(entity.getInternalId());

	mapper.setExternalId(entity.getExternalId());
	mapper.setModule(entity.getModule());

	mapperDAO.create(mapper);
    }

    private static <T extends DomainObject, E extends Importable<T>, DAO extends GenericDAO<T>> MappingStrategy<E> createMappingStrategy(
	    final IMapperProcessor<T, E, DAO> processor) {
	return processor.createMappingStrategy();
    }
}