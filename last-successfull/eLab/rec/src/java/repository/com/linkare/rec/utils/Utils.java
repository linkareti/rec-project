package com.linkare.rec.utils;

import com.linkare.rec.data.acquisition.PhysicsVal;
import com.linkare.rec.export.domain.EBooleanVal;
import com.linkare.rec.export.domain.EByteArrayVal;
import com.linkare.rec.export.domain.EByteVal;
import com.linkare.rec.export.domain.EDoubleVal;
import com.linkare.rec.export.domain.EFloatVal;
import com.linkare.rec.export.domain.EIntVal;
import com.linkare.rec.export.domain.ELongVal;
import com.linkare.rec.export.domain.EPhysicsVal;
import com.linkare.rec.export.domain.EShortVal;
import com.linkare.rec.export.domain.embeddable.EmbByteArrayValue;
import com.linkare.rec.export.domain.enums.EnumPhysicsValueType;
import com.linkare.rec.export.exceptions.InvalidTypeValException;

/**
 * This class will include some utilities methods to mapping 
 * ReCMultiCastDataProducer to EReCMultiCastDataProducer 
 * 
 * hard work! :S
 * 
 * @author artur
 */
public class Utils {

//    /**
//     * Mapping ReCMultiCastDataProducer to EReCMultiCastDataProducer. 
//     * 
//     * 
//     * 
//     * @param vo
//     * @return
//     */
//    public static EReCMultiCastDataProducer reCMultiCastDataProducerToEntity(ReCMultiCastDataProducer vo) throws InvalidTypeValException {
//        EReCMultiCastDataProducer entReCMultiCastDataProducer = null;
//        try {
//            entReCMultiCastDataProducer = new EReCMultiCastDataProducer();
//            entReCMultiCastDataProducer.setCachedDataProducerName(vo.getDataProducerName());
//            entReCMultiCastDataProducer.setOid(vo.getOID());
//
//
//            EHardwareAcquisitionConfig entHardwareAcquisitionConfig = new EHardwareAcquisitionConfig();
//            entHardwareAcquisitionConfig.setTimeStart(dateTimeToEmbDateTime(vo.getAcquisitionHeader().getTimeStart()));
//            entHardwareAcquisitionConfig.setSelectedHardwareParameters(convertingArrayTolist(vo.getAcquisitionHeader().getSelectedHardwareParameters(), true));
//            entHardwareAcquisitionConfig.setTotalSamples(vo.getAcquisitionHeader().getTotalSamples());
//            entHardwareAcquisitionConfig.setChannelsConfig(convertingArrayTolist(vo.getAcquisitionHeader().getChannelsConfig(), false));
//            entHardwareAcquisitionConfig.setFamiliarName(vo.getAcquisitionHeader().getFamiliarName());
//            entHardwareAcquisitionConfig.setHardwareUniqueID(vo.getAcquisitionHeader().getHardwareUniqueID());
//
//            entReCMultiCastDataProducer.setCachedAcqHeader(entHardwareAcquisitionConfig);
//
//
//        } catch (NotAvailableException ex) {
//            Logger.getLogger(Utils.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return entReCMultiCastDataProducer;
//    }
//
//    /**
//     * Convert array to List. 
//     * Martelei este
//     * 
//     * @param arr
//     * @return List of EParameterConfig or List of EChannelAcquisitionConfig.       
//     * 
//     * 
//     * 
//     */
//    public static List convertingArrayTolist(Object[] arr, boolean isHardwareParameters) throws InvalidTypeValException {
//        List myList = null;
//        if (arr != null && arr[0] != null) {
//            if (arr[0] instanceof ParameterConfig) {
//                if (isHardwareParameters) {
//                    myList = new ArrayList<EHardwareParameterConfig>();
//                } else {
//                    myList = new ArrayList<EChannelParameterConfig>();
//                }
//                for (Object elem : arr) {
//                    myList.add(parameterConfigToEntity((ParameterConfig) elem, isHardwareParameters));
//                }
//            } else if (arr[0] instanceof ChannelAcquisitionConfig) {
//                myList = new ArrayList<EChannelAcquisitionConfig>();
//                for (Object elem : arr) {
//                    myList.add(channelAcquisitionConfigToEntity((ChannelAcquisitionConfig) elem));
//                }
//            }
//        }
//        return myList;
//    }
//
//    /**
//     * Mapping ChannelAcquisitionConfig to EChannelAcquisitionConfig
//     * 
//     * 
//     * @param vo
//     * @return
//     */
//    public static EChannelAcquisitionConfig channelAcquisitionConfigToEntity(ChannelAcquisitionConfig vo) throws InvalidTypeValException {
//        EChannelAcquisitionConfig eChannelAcquisitionConfig = new EChannelAcquisitionConfig();
//        eChannelAcquisitionConfig.setChannelName(vo.getChannelName());
//        eChannelAcquisitionConfig.setSelectedChannelParameters(convertingArrayTolist(vo.getSelectedChannelParameters(), false));
//        eChannelAcquisitionConfig.setSelectedFrequency(frequencyToEmbFrequency(vo.getSelectedFrequency()));
//        eChannelAcquisitionConfig.setSelectedScale(scaleToEntity(vo.getSelectedScale()));
//        eChannelAcquisitionConfig.setTimeStart(dateTimeToEmbDateTime(vo.getTimeStart()));
//        eChannelAcquisitionConfig.setTotalSamples(vo.getTotalSamples());
//        return eChannelAcquisitionConfig;
//    }
//
//    /**
//     * Mapping DateTime to EmbDateTime
//     * 
//     * 
//     * @param dateTime
//     * @return
//     */
//    public static EmbDateTime dateTimeToEmbDateTime(DateTime dateTime) {
//        EmbDateTime eDateTime = new EmbDateTime();
//
////        eDateTime.setDay(dateTime.getDate().getDay());
////        eDateTime.setMonth(dateTime.getDate().getMonth());
////        eDateTime.setYear(dateTime.getDate().getYear());
////
////        eDateTime.setHours(dateTime.getTime().getHours());
////        eDateTime.setMicros(dateTime.getTime().getMicros());
////        eDateTime.setMilis(dateTime.getTime().getMilis());
////        eDateTime.setMinutes(dateTime.getTime().getMinutes());
////        eDateTime.setNanos(dateTime.getTime().getNanos());
////        eDateTime.setPicos(dateTime.getTime().getPicos());
////        eDateTime.setSeconds(dateTime.getTime().getSeconds());
//
//        return eDateTime;
//    }
//
//    /**
//     * Mapping Frequency to EmbFrequency
//     * 
//     * 
//     * @param vo
//     * @return
//     */
//    public static EmbFrequency frequencyToEmbFrequency(Frequency vo) {
//        EmbFrequency embFrequency = new EmbFrequency();
//        embFrequency.setFrequency(vo.getFrequency());
//        //i thinks this will work!!
//        embFrequency.setAppliedMultiplier(EnumMultiplier.values()[vo.getMultiplier().getValue()]);
//        embFrequency.setFrequencyDefType(EnumFrequencyDefType.values()[vo.getFrequencyDefType().getValue()]);
//        return embFrequency;
//    }
//
//    /**
//     * Mapping Scale to EScale
//     * 
//     * @param scale
//     * @return
//     */
//    public static EScale scaleToEntity(Scale scale) throws InvalidTypeValException {
//        EScale escale = new EScale();
//        escale.setAppliedMultiplier(EnumMultiplier.values()[scale.getMultiplier().getValue()]);
//        escale.setDefaultError(physicsValToEntity(scale.getDefaultErrorValue()));
//        escale.setMaxValue(physicsValToEntity(scale.getMaximumValue()));
//        escale.setMinValue(physicsValToEntity(scale.getMinimumValue()));
//        escale.setPhysicsUnitName(scale.getPhysicsUnitName());
//        escale.setPhysicsUnitSymbol(scale.getPhysicsUnitSymbol());
//        escale.setScaleLabel(scale.getScaleLabel());
//        escale.setStep(physicsValToEntity(scale.getStepValue()));
//        return escale;
//    }

    
    
    /**
     * 
     * 
     * @param physicsVal
     * @return  EPhysicsVal. Null if physicsVal or physicsVal.getDiscriminator() are null.
     * @throws com.linkare.rec.exceptions.InvalidTypeValException
     */
    public static EPhysicsVal physicsValToEntity(PhysicsVal physicsVal) throws InvalidTypeValException {
        if (physicsVal != null && physicsVal.getDiscriminator() != null) {
            try {
                switch (EnumPhysicsValueType.values()[physicsVal.getDiscriminator().getValue()]) {
                    case BOOLEAN_VAL:
                        EBooleanVal eBooleanVal = new EBooleanVal();
                        eBooleanVal.setValue(physicsVal.isBooleanValue());
                        return eBooleanVal;
                    case BYTE_VAL:
                        EByteVal eByteVal = new EByteVal();
                        eByteVal.setValue(physicsVal.getByteValue());
                        return eByteVal;
                    case SHORT_VAL:
                        EShortVal shortVal = new EShortVal();
                        shortVal.setValue(physicsVal.getShortValue());
                        return shortVal;
                    case INT_VAL:
                        EIntVal eIntVal = new EIntVal();
                        eIntVal.setValue(physicsVal.getIntValue());
                        return eIntVal;
                    case LONG_VAL:
                        ELongVal eLongVal = new ELongVal();
                        eLongVal.setValue(physicsVal.getLongValue());
                        return eLongVal;
                    case FLOAT_VAL:
                        EFloatVal eFloatVal = new EFloatVal();
                        eFloatVal.setValue(physicsVal.getFloatValue());
                        return eFloatVal;
                    case DOUBLE_VAL:
                        EDoubleVal eDoubleVal = new EDoubleVal();
                        eDoubleVal.setValue(physicsVal.getDoubleValue());
                        return eDoubleVal;
                    case BYTEARRAY_VAL:
                        EByteArrayVal eByteArrayVal = new EByteArrayVal();
                        eByteArrayVal.setValue(new EmbByteArrayValue(physicsVal.getByteArrayValue()));
                        return eByteArrayVal;
                    default:
                        throw new InvalidTypeValException();
                }
            } catch (IndexOutOfBoundsException ioob) {
                throw new InvalidTypeValException("physicsVal.getDiscriminator().getValue(): " + physicsVal.getDiscriminator().getValue());
            }
        }else
            return null;

    }//    /**
//     * Mapping ByteArrayValue to EmbByteArrayValue
//     * 
//     * @param byteArrayValue
//     * @return
//     */
//    public static EmbByteArrayValue byteArrayValueToEmbByteArrayValue(ByteArrayValue byteArrayValue) {
//        EmbByteArrayValue embByteArrayValue = new EmbByteArrayValue();
//        embByteArrayValue.setData(byteArrayValue.getData());
//        embByteArrayValue.setMimeType(byteArrayValue.getMimeType());
//        return embByteArrayValue;
//    }
//
//    /**
//     * Mapping ParameterConfig to EParameterConfig
//     * 
//     * @param parameterConfig
//     * @return
//     */
//    public static EParameterConfig parameterConfigToEntity(ParameterConfig parameterConfig, boolean isHardware) {
//        EParameterConfig eParameterConfig = null;
//        if (isHardware) {
//            eParameterConfig = new EHardwareParameterConfig();
//        } else {
//            eParameterConfig = new EChannelParameterConfig();
//        }
//        eParameterConfig.setParameterName(parameterConfig.getParameterName());
//        eParameterConfig.setParameterValue(parameterConfig.getParameterValue());
//        return eParameterConfig;
//    }
//    public static String toString(Object o) {
//        StringBuilder sb = new StringBuilder();
//        Class c = o.getClass();
//        java.lang.reflect.Field[] fields = c.getDeclaredFields();
//        java.lang.reflect.Method[] methods = c.getMethods();
//        methods[0].invoke(o);
//        
//        for (Field field : fields) {
//
//            sb.append("[");
//            sb.append(field.getName());
//            sb.append(":");
//            String aux = field.getName().toUpperCase();
//            
//          
//            if (Collection.class.isAssignableFrom(field.getType())) {
//                    sb.append("{");
//                    sb.append();
//                    sb.append("}");
//            } else if (field.getClass().isPrimitive()) {
//                sb.append(sb);
//            } else {
//                sb.append("null");
//            }
//
//            sb.append("]\n");
//        }
//
//
//        return sb.toString();
//
//    }
//    public  static String toString(Object o){
//        BeanInfo bi = 
//        
//        
//        
//        return null;
//    }
//
//    public static void main(String[] args) {
//       MyClass m = new MyClass();
//       m.setList();
//       System.out.println(toString(m.getClass()));
//
//
//
//    }
//    
//    
//    private static class MyClass{
//        private int inteiro=1;
//        private boolean booleano= false;
//        List<String> myList = new ArrayList<String>();
//       
//        
//        private void setList(){
//            myList.add("xico");
//            myList.add("manuel");
//        }
//        
//    }
}































