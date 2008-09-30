/*
 * ApparatusConfigCollection.java
 *
 * Created on 1 de Julho de 2003, 17:42
 */

package old.configOld;

/**
 *
 * @author José Pedro Pereira - Linkare TI
 */
public class ApparatusConfigCollection 
{
    private java.util.Hashtable apparatusConfigs=new java.util.Hashtable();
    
    /** Creates a new instance of ApparatusConfigCollection */
    public ApparatusConfigCollection()
    {
    }
    
    public void addApparatusConfig(ApparatusConfig config)
    {
	apparatusConfigs.put(config.getId(),config);
    }
    
    public ApparatusConfig getApparatusConfig(String apparatusID)
    {
	return (ApparatusConfig)apparatusConfigs.get(apparatusID);
    }
}
