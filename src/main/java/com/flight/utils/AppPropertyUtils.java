package com.flight.utils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class AppPropertyUtils extends PropertyUtils {

	private final static Log log = LogFactory.getLog(PropertyUtils.class);

	public static void copyPropertiesIgnoreNull(Object dest, Object orig)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {

		if (dest == null) {
			throw new IllegalArgumentException("No destination bean specified");
		}
		if (orig == null) {
			throw new IllegalArgumentException("No origin bean specified");
		}

		PropertyDescriptor[] origDescriptors = getPropertyDescriptors(orig);
		for (int i = 0; i < origDescriptors.length; i++) {
			String name = origDescriptors[i].getName();
			if (isReadable(orig, name) && isWriteable(dest, name)) {
				try {
					Object value = getSimpleProperty(orig, name);
					if(StringUtils.isNotEmpty(String.valueOf(value))){
						if (dest instanceof DynaBean) {
							((DynaBean) dest).set(name, value);
						} else {
							setSimpleProperty(dest, name, value);
						}
					}
				} catch (NoSuchMethodException e) {
					if (log.isDebugEnabled()) {
						log.debug("Error writing to '" + name + "' on class '" + dest.getClass() + "'", e);
					}
				}
			}
		}
	}

}
