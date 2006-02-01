#include "pt_utl_ist_ie_elab_Driver_solitoes_DriverObject.h"

jobject globalRefCallBackObject;
JavaVM* g_pJvm=NULL;
jmethodID mID_stateChange;
jmethodID mID_newSamples;

/*
 * Class:     pt_utl_ist_ie_elab_Driver_solitoes_DriverObject
 * Method:    J2C_init
 * Signature: ()V
 */
JNIEXPORT void JNICALL 
Java_pt_utl_ist_ie_elab_Driver_solitoes_DriverObject_J2C_1init
  (JNIEnv *env, jobject obj)
{
    jclass classCallBackObject=(*env)->GetObjectClass(env,obj);
    if(classCallBackObject==0)
    {
        /*Error - Throw Exception on init() */
    }

    mID_stateChange=(*p_Env)->GetMethodId(pEnv,classCallBackObject,"C2J_stateChange","(B)V");
    mID_newSamples=(*p_Env)->GetMethodId(pEnv,classCallBackObject,"C2J_newSamples","([[I)V");

    globalRefCallBackObject=(*env)->NewGlobalRef(env,obj);
    
    (*env)->GetJavaVM(&g_pJvm);

    if(globalRefCallBackObjectClass==0)
    {
        /*Error - Throw Exception on init() */
    }

    //Do someother initialization
    //SolitoesDriverInit();

}

/*
 * Class:     pt_utl_ist_ie_elab_Driver_solitoes_DriverObject
 * Method:    J2C_shutdown
 * Signature: ()V
 */
JNIEXPORT void JNICALL 
Java_pt_utl_ist_ie_elab_Driver_solitoes_DriverObject_J2C_1shutdown
  (JNIEnv *env, jobject obj)
{
    //Shutdown the global reference - let the JVM 
    //Garbage Collector collect the object for trash
    
    if(globalRefCallBackObjectClass!=0)
        (*env)->DeleteGlobalRef(env,globalRefCallBackObjectClass);

    g_pJvm=NULL;
    //Do someother shutdown
    //SolitoesDriverShutdown();

}


void C2J_stateChange(jbyte newHardwareState)
{
    JNIEnv *p_Env=getJNIEnv();
    
    (*p_Env)->CallVoidMethod(pEnv,globalRefCallBackObject,mID_stateChange,newHardwareState);

    releaseJNIEnv();
}

void C2J_newSamples(jint[][] channel_indexes)
{
    JNIEnv *p_Env=getJNIEnv();
    
    (*p_Env)->CallVoidMethod(pEnv,globalRefCallBackObject,mID_newSamples,channel_indexes);

    releaseJNIEnv();
}

JNIEnv *getJNIEnv()
{
    JNIEnv* pEnv=NULL;

    g_pJvm->AttachCurrentThread((void **) &pEnv,NULL);
    
    return return_pEnv;
}

void releaseJNIEnv()
{
    g_pJvm->DetachCurrentThread();
}
