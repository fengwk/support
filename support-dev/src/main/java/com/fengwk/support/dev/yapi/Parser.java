package com.fengwk.support.dev.yapi;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.reflect.TypeUtils;

import com.fengwk.support.core.reflect.GenericArrayTypeImpl;
import com.fengwk.support.core.reflect.ParameterizedTypeImpl;

/**
 * @author fengwk
 */
public class Parser {
	
	public static Base parse(Type t) {
		return parse(t, new LinkedList<>());
	}
	
	private static Base parse(Type t, Deque<Type> cycleStack) {
		// Gene
		if (t == String.class) {
			return new Gene("string");
		}
		if (t == int.class
				|| t == Integer.class) {
			return new Gene("integer");
		}
		if (t == long.class
				|| t == Long.class) {
			return new Gene("long");
		}
		if (t == double.class
				|| t == Double.class) {
			return new Gene("double");
		}
		if (t == float.class
				|| t == Float.class) {
			return new Gene("float");
		}
		if (TypeUtils.isAssignable(Number.class, t)) {
			return new Gene("number");
		}
		if (t == boolean.class
				|| t == Boolean.class) {
			return new Gene("boolean");
		}
		if (t == Date.class
				|| t == LocalDate.class
				|| t == LocalDateTime.class) {
			return new Gene("date");
		}
		
		// Arr
		if (t instanceof GenericArrayType) {
			GenericArrayType gat = (GenericArrayType) t;
			Type gct = gat.getGenericComponentType();
			boolean p = pushCycleStack(gct, cycleStack);
			Arr arr = new Arr(parse(gct, cycleStack));
			if (p)
				cycleStack.pop();
			return arr;
		}
		if (TypeUtils.isAssignable(Collection.class, t)) {
			ParameterizedType pt = (ParameterizedType) t;
			Type[] atas = pt.getActualTypeArguments();
			if (atas.length == 0)
				throw new RuntimeException("未指定泛型:" + t);
			Type at = atas[0];
			boolean p = pushCycleStack(at, cycleStack);
			Arr arr = new Arr(parse(at, cycleStack));
			if (p)
				cycleStack.pop();
			return arr;
		}
		if (t instanceof Class) {
			Class<?> c = (Class<?>) t;
			if (c.isArray()) {
				Class<?> ct = c.getComponentType();
				boolean p = pushCycleStack(ct, cycleStack);
				Arr arr = new Arr(parse((Type) ct, cycleStack));
				if (p)
					cycleStack.pop();
				return arr;
			}
		}
		
		// Obj
		OTypeInfo oti = getOTypeInfo(t);
		Obj obj = new Obj(oti.objClass.getSimpleName(), new LinkedHashMap<>(), new ArrayList<>());
		boolean p = pushCycleStack(t, cycleStack);
		parseObj(obj, oti, cycleStack);
		if (p)
			cycleStack.pop();
		return obj;
	}
	
	private static boolean pushCycleStack(Type t, Deque<Type> cycleStack) {
		if (TypeUtils.isAssignable(Collection.class, t))
			return false;
		cycleStack.push(t);
		return true;
	}
	
	private static OTypeInfo getOTypeInfo(Type t) {
		OTypeInfo oti = new OTypeInfo();
		if (t instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) t;
			oti.objClass = (Class<?>) pt.getRawType();
			TypeVariable<?>[] tvs = oti.objClass.getTypeParameters();
			Type[] ts = pt.getActualTypeArguments();
			oti.tvtMap = new HashMap<>();
			for (int i = 0; i < tvs.length; i++) {
				oti.tvtMap.put(tvs[i], ts[i]);
			}
		} else if (t instanceof Class<?>) {
			oti.objClass = (Class<?>) t;
		} else {
			throw new RuntimeException("不支持的类型:" + t);
		}
		return oti;
	}
	
	public static void parseObj(Obj obj, OTypeInfo oti, Deque<Type> cycleStack) {
		Type supType = oti.objClass.getGenericSuperclass();
		if (Object.class != supType) {
			OTypeInfo supOti = getOTypeInfo(supType);
			parseObj(obj, supOti, cycleStack);
		}
		
		SourceReader reader = new SourceReader(oti.objClass);
		try {
			reader.init();
		} catch (Exception e) {
			// ignore
		}
		
		Field[] fs = oti.objClass.getDeclaredFields();
		for (Field f : fs) {
			String fname = f.getName();
			
			if (isFilter(f))
				continue;
			
			if (isRequired(f.getAnnotations()))
				obj.required.add(fname);
			
			Type rFtype = rType(f.getGenericType(), oti.tvtMap);
			if (cycleStack.contains(rFtype)) {
				obj.properties.put(fname, new Obj("$ref=" + rFtype.toString(), new LinkedHashMap<>(), new ArrayList<>()));
				continue;
			}
			boolean p = pushCycleStack(rFtype, cycleStack);
			Base b = parse(rFtype, cycleStack);
			if (p)
				cycleStack.pop();
			
			List<String> comments = reader.findComment(fname);
			b.description = parseDescription(comments);
			b.introduction = parserIntroduction(comments);
			obj.properties.put(fname, b);
		}
	}
	
	protected static String parseDescription(List<String> comments) {
		if (CollectionUtils.isEmpty(comments))
			return null;
		return comments.get(0);
	}
	
	protected static String parserIntroduction(List<String> comments) {
		if (comments != null && comments.size() > 1)
			return comments.get(comments.size() - 1);
		return null;
	}
	
	private static Type rType(Type t, Map<TypeVariable<?>, Type> tvtMap) {
		if (t instanceof ParameterizedType) {
			ParameterizedType pt = (ParameterizedType) t;
			Type ot = pt.getOwnerType();
			Type at = pt.getRawType();
			Type[] atas = pt.getActualTypeArguments();
			
			Type rOt = ot == null ? null : rType(ot, tvtMap);
			Type rAt = rType(at, tvtMap);
			
			boolean isM = !Objects.equals(ot, rOt) || !at.equals(rAt);
			Type[] rAtas = new Type[atas.length];
			for (int i = 0; i < atas.length; i++) {
				Type ata = atas[i];
				Type rAta = rType(ata, tvtMap);
				rAtas[i] = rAta;
				if (!ata.equals(rAta))
					isM = true;
			}
			
			if (isM) 
				return new ParameterizedTypeImpl(rAtas, rOt, rAt);
			return t;
		} else if (t instanceof GenericArrayType) {
			GenericArrayType gat = (GenericArrayType) t;
			Type gct = gat.getGenericComponentType();
			Type rGct = rType(gct, tvtMap);
			if (gct.equals(rGct))
				return gct;
			return new GenericArrayTypeImpl(rGct);
		} else if (t instanceof TypeVariable)
			return tvtMap.get((TypeVariable<?>) t);
		else if (t instanceof Class<?>)
			return t;
		else
			throw new RuntimeException("不支持的类型:" + t);
	}
	
	protected static boolean isFilter(Field f) {
		return "serialVersionUID".equals(f.getName()) || Modifier.isStatic(f.getModifiers());
	}
	
	protected static boolean isRequired(Annotation[] anns) {
		for (Annotation ann : anns) {
			if (ann instanceof NotNull) {
				return true;
			}
			if (ann instanceof NotEmpty) {
				return true;
			}
		}
		return false;
	}
	
	static class OTypeInfo {
		Class<?> objClass;
		Map<TypeVariable<?>, Type> tvtMap = Collections.emptyMap();
	}
	
}
