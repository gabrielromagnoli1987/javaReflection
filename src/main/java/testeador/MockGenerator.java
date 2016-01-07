package testeador;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import annotations.Mock;
import annotations.MockStringAttribute;
import annotations.MockTodayAttribute;


public class MockGenerator {
	
	public static <T> List<T> createMockInstances(Class<T> clazz, int cant) {
		
		List<T> list = new ArrayList<T>();
		
		if(clazz.isAnnotationPresent(Mock.class)) {
			Random random = new Random();
			
			for(int i = 0; i < cant; i++) {
				
				try {

					// constructor especifico - se pide un constructor que cumpla con la signatura pasada como parametro (representada por un arreglo)
					Constructor<?> constructor = clazz.getConstructor(new Class[]{String.class, Date.class});
					
					int index = random.nextInt(2);
					
					String text = "";
					Date today = null;
					
					for(Field field : clazz.getDeclaredFields()) {
						if(field.isAnnotationPresent(MockTodayAttribute.class)) {
							today = new Date();
						}
						if(field.isAnnotationPresent(MockStringAttribute.class)) {
							text = field.getAnnotation(MockStringAttribute.class).value()[index];
						}
					}
					
					if(! text.equals("") && today != null) {
						Object object = constructor.newInstance(new Object[] {text, today});					
						list.add((T) object);
					}
					else {
						break; // salgo del for si en la primer vuelta del for compruebo que no existen las annotations de mi interes
					}

				} catch (SecurityException e1) {
					e1.printStackTrace();
					break;
				} catch (InstantiationException e) {						
					e.printStackTrace();
					break;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					break;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					break;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					break;
				}
				// constructor especifico
				catch (NoSuchMethodException e) {					
					e.printStackTrace();
				}
				
			} // for
			
		} // if
		
		return list;
	}

}
