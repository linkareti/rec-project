package com.linkare.irn.nascimento.web.fasp;

import java.io.Serializable;

/**
 * @author Paulo Zenida - Linkare TI
 *
 */
public class FARequestedAttribute implements Serializable {

    private static final long serialVersionUID = -4083998656948558264L;

    public static final String MDC_CIDADAO = "http://interop.gov.pt/MDC/Cidadao/";

    public static final String NOME_PROPRIO = "NomeProprio";
    public static final String NOME_APELIDO = "NomeApelido";
    public static final String NIC = "NIC";
    public static final String NUMERO_SERIE = "NumeroSerie";
    public static final String NO_DOCUMENTO = "NoDocumento";
    public static final String TIPO_DOCUMENTO = "TipoDocumento";
    public static final String NUMERO_DE_CONTROLO = "NumeroDeControlo";
    public static final String DATA_NASCIMENTO = "DataNascimento";
    public static final String NACIONALIDADE = "Nacionalidade";

    public static final String TIPO_DE_VIA = "TipoDeVia";
    public static final String DESIGNACAO_DA_VIA = "DesignacaoDaVia";
    public static final String TIPO_EDIFICIO = "TipoEdificio";
    public static final String NUMERO_PORTA = "NumeroPorta";
    public static final String ANDAR = "Andar";
    public static final String LADO = "Lado";
    public static final String CODIGO_POSTAL4 = "CodigoPostal4";
    public static final String CODIGO_POSTAL3 = "CodigoPostal3";
    public static final String LOCALIDADE = "Localidade";

    public static final String NOME_PROPRIO_PAI = "NomeProprioPai";
    public static final String NOME_APELIDO_PAI = "NomeApelidoPai";

    public static final String NOME_PROPRIO_MAE = "NomeProprioMae";
    public static final String NOME_APELIDO_MAE = "NomeApelidoMae";

    public static final String DISTRITO = "Distrito";
    public static final String CONCELHO = "Concelho";
    public static final String FREGUESIA = "Freguesia";

    private String prefix;

    private String friendlyName;

    private boolean required;

    private String value;

    public static FARequestedAttribute create(String friendlyName, boolean required) {
	return new FARequestedAttribute(MDC_CIDADAO, friendlyName, required);
    }

    public static FARequestedAttribute create(String fullName, String value) {
	String friendlyName = fullName.replaceFirst(MDC_CIDADAO, "");
	return new FARequestedAttribute(MDC_CIDADAO, friendlyName, value);
    }

    public FARequestedAttribute(String prefix, String friendlyName, boolean required) {
	super();
	this.prefix = prefix;
	this.friendlyName = friendlyName;
	this.required = required;
    }

    public FARequestedAttribute(String prefix, String friendlyName, String value) {
	super();
	this.prefix = prefix;
	this.friendlyName = friendlyName;
	this.value = value;
    }

    public String getFriendlyName() {
	return friendlyName;
    }

    public void setFriendlyName(String friendlyName) {
	this.friendlyName = friendlyName;
    }

    public String getName() {
	return prefix + friendlyName;
    }

    public boolean isRequired() {
	return required;
    }

    public void setRequired(boolean required) {
	this.required = required;
    }

    public String getValue() {
	return value;
    }

    public void setValue(String value) {
	this.value = value;
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + ((friendlyName == null) ? 0 : friendlyName.hashCode());
	result = prime * result + (required ? 1231 : 1237);
	result = prime * result + ((value == null) ? 0 : value.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	FARequestedAttribute other = (FARequestedAttribute) obj;
	if (friendlyName == null) {
	    if (other.friendlyName != null)
		return false;
	} else if (!friendlyName.equals(other.friendlyName))
	    return false;
	if (required != other.required)
	    return false;
	if (value == null) {
	    if (other.value != null)
		return false;
	} else if (!value.equals(other.value))
	    return false;
	return true;
    }

    @Override
    public String toString() {
	return "FARequestedAttribute [friendlyName=" + friendlyName + ", value=" + value + "]";
    }
}