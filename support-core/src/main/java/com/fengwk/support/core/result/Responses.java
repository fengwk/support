package com.fengwk.support.core.result;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.fengwk.support.core.json.JsonUtils;
import com.fengwk.support.core.reflect.ParameterizedTypeImpl;

import lombok.Data;

/**
 * 
 * @author fengwk
 * @version V1.0
 * @since 2019-08-11 10:34
 */
public final class Responses {

	/**
	 * 成功码
	 */
	private static final String SUCCESS_CODE = "0";

	/**
	 * 成功消息
	 */
	private static final String SUCCESS_MESSAGE = "成功";

	/**
	 * 成功
	 * 
	 * @return
	 */
	public static <T> Response<T> success() {
		return result(SUCCESS_CODE, null, SUCCESS_MESSAGE);
	}

	/**
	 * 成功
	 * 
	 * @param data
	 * @return
	 */
	public static <T> Response<T> success(T data) {
		return result(SUCCESS_CODE, data, SUCCESS_MESSAGE);
	}

	/**
	 * 成功
	 * 
	 * @param data
	 * @param message
	 * @return
	 */
	public static <T> Response<T> success(T data, String message) {
		return result(SUCCESS_CODE, data, message);
	}

	/**
	 * 失败
	 * 
	 * @param message
	 * @return
	 */
	public static <T> Response<T> failure(String code, String message) {
		return result(code, null, message);
	}
	
	/**
     * 失败
     * 
     * @param message
     * @return
     */
    public static <T> Response<T> failure(String code, T data, String message) {
        return result(code, data, message);
    }

	/**
	 * 构造
	 * 
	 * @param code
	 * @param data
	 * @param message
	 * @return
	 */
	private static <T> Response<T> result(String code, T data, String message) {
	    return new ResponseImpl<T>(code, message, data);
	}

	/**
	 * 解析
	 * 
	 * @param json
	 * @param dataType
	 * @return
	 */
	public static <T> Response<T> parse(String json, Type dataType) {
		if (json == null) {
			return null;
		}
		ParameterizedType typeOfT = new ParameterizedTypeImpl(new Type[] { dataType }, Responses.class, ResponseImpl.class);
		return JsonUtils.fromJson(json, typeOfT);
	}

	/**
	 * 
	 * @author fengwk
	 */
	@Data
	public static final class ResponseImpl<T> implements Response<T> {

		private static final long serialVersionUID = -6520214782056141460L;

		private String code;
		private String message;
		private T data;

		public ResponseImpl(String code, String message, T data) {
            this.code = code;
            this.message = message;
            this.data = data;
        }
		
        @Override
        public boolean isSuccess() {
            return SUCCESS_CODE.equals(code);
        }

	}
	
}
