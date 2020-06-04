package com.linkare.irn.nascimento.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Set;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Paulo Zenida - Linkare TI
 *
 */
public final class CDIUtils {

    private static final String PROXY_PROXY_CLASS_FQN = "org.jboss.weld.bean.proxy.ProxyObject";

    private static final String PROXY_CLASS_MARKER = "$Proxy$";

    private static final Logger LOG = LoggerFactory.getLogger(CDIUtils.class);

    public static final String BEAN_MANAGER_LOOKUP_NAME = "java:comp/BeanManager";

    private static final BeanManager BEAN_MANAGER;

    static {
	BEAN_MANAGER = lookupBeanManager();
    }

    private CDIUtils() {
    }

    public static <T> T getCdiBean(final Class<T> beanClass) {
	return getCdiBean(beanClass, BEAN_MANAGER);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCdiBean(final Class<T> beanClass, final BeanManager beanManager) {
	final Iterator<Bean<?>> it = beanManager.getBeans(beanClass).iterator();
	if (it.hasNext()) {
	    final Bean<T> cdiBean = (Bean<T>) it.next();
	    return getCdiBean(cdiBean, beanManager);
	}
	return null;
    }

    public static <T> T getCdiBean(final String beanName) {
	return getCdiBean(beanName, BEAN_MANAGER);
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCdiBean(final String beanName, final BeanManager beanManager) {
	final Iterator<Bean<?>> it = beanManager.getBeans(beanName).iterator();
	if (it.hasNext()) {
	    final Bean<T> cdiBean = (Bean<T>) it.next();
	    return getCdiBean(cdiBean, beanManager);
	}
	return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getCdiBean(final Bean<T> bean, final BeanManager beanManager) {
	final CreationalContext<T> context = beanManager.createCreationalContext(bean);
	final Class<?> cdiBeanClass = getCdiBeanClass(bean);
	return (T) beanManager.getReference(bean, cdiBeanClass, context);
    }

    /**
     * This method calculates automagically the bean class that should be used by the client code, based only on the bean that is set as argument. Its
     * implementation consists on the return of the unique class of the list of bean types that are assignable to all others, i.e., the most specific
     * class/interface.
     * 
     * @param bean
     * @return
     */
    public static Class<?> getCdiBeanClass(final Bean<?> bean) {
	Class<?> result = null;
	for (final Type childType : bean.getTypes()) {
	    if (childType instanceof Class<?>) {
		if (result == null || result.isAssignableFrom((Class<?>) childType)) {
		    result = (Class<?>) childType;
		}
	    }
	}
	return result;
    }

    private static BeanManager lookupBeanManager() {
	try {
	    final InitialContext ic = new InitialContext();
	    final BeanManager beanManager = (BeanManager) ic.lookup(BEAN_MANAGER_LOOKUP_NAME);
	    return beanManager;
	} catch (NamingException e) {
	    LOG.error(String.format("Error looking up beanManager from JNDI with lookup name %s", BEAN_MANAGER_LOOKUP_NAME), e);
	    return null;
	}
    }

    public static void fireEvent(final Object event, final Annotation... qualifiers) {
	BEAN_MANAGER.fireEvent(event, qualifiers);
    }

    public static <T> T getQualifierAnnotation(Set<Annotation> qualifiers, Class<T> annotation) {
	for (Annotation annotationQualifier : qualifiers) {
	    if (annotation.isAssignableFrom(annotationQualifier.getClass())) {
		return annotation.cast(annotationQualifier);
	    }
	}
	return null;
    }

    public static boolean isProxy(Object obj) {

	if (obj == null) {
	    throw new IllegalArgumentException("obj cannot be null");
	}

	try {
	    return Class.forName(PROXY_PROXY_CLASS_FQN).isInstance(obj);
	} catch (Exception e) {
	    LOG.error("Unable to check if object is proxy through proxy class", e);
	    return obj.getClass().getSimpleName().contains(PROXY_CLASS_MARKER);
	}
    }
}