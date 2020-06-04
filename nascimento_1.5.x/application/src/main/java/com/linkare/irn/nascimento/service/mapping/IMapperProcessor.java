package com.linkare.irn.nascimento.service.mapping;

import java.io.InputStream;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.validation.ValidationException;

import com.linkare.irn.nascimento.dao.GenericDAO;
import com.linkare.irn.nascimento.model.DomainObject;
import com.linkare.irn.nascimento.model.ext.mapping.Importable;
import com.linkare.irn.nascimento.model.ext.mapping.MappingProcessResult;
import com.linkare.irn.nascimento.service.BaseStatelessService;

import au.com.bytecode.opencsv.bean.MappingStrategy;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public interface IMapperProcessor<ENTITY extends DomainObject, DTO extends Importable<ENTITY>, DAO extends GenericDAO<ENTITY>>
	extends BaseStatelessService<ENTITY, DAO> {

    public MappingProcessResult load(final InputStream in);

    public default ENTITY getOrCreateEntity(final DTO importable) {
	return importable.toEntity();
    }

    public default MappingStrategy<DTO> createMappingStrategy() {
	final Map<String, String> columnMapping = new LinkedHashMap<String, String>();
	mapCsvColumnsToProperties(columnMapping);

	final ApplicationMappingStrategy<DTO> strategy = new ApplicationMappingStrategy<DTO>();
	strategy.setType(getType());
	strategy.setColumnMapping(columnMapping);
	return strategy;
    }

    /**
     * @param columnMapping
     */
    public void mapCsvColumnsToProperties(final Map<String, String> columnMapping);

    /**
     * This method must be overridden as needed to make additional mappings. For instance, to lookup for a database entity and set it to the appropriate entity
     * 
     * @param entity
     *            the source entity to which additional mappings are needed
     */
    public default void doAdditionalMappings(final DTO entry, final ENTITY entity) {
	// do nothing by default...
    }

    /**
     * This method validates the passed in entity to check if the persist may be performed on that entity. This is the method that should check for previously
     * existing entities so that no duplicates are created.
     * 
     * @param entry
     *            the entry to be validated
     * @param entity
     *            the entity to be validated
     * @throws ValidationException
     */
    public void doValidation(final DTO entry, final ENTITY entity);

    /**
     * 
     * @return the type of the class to be instantiated that represents each CSV line
     */
    public Class<DTO> getType();
}