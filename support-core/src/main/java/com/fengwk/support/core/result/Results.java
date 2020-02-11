package com.fengwk.support.core.result;

import com.fengwk.support.core.code.Code;
import com.fengwk.support.core.code.ErrorCode;
import com.fengwk.support.core.code.SuccessCode;

import lombok.Data;

/**
 * 
 * @author fengwk
 * @version V1.0
 * @since 2019-08-11 10:34
 */
public final class Results {

	/**
	 * 成功结果
	 * 
	 * @return
	 */
	public static <T> Result<T> success() {
		return new ResultImpl<T>(SuccessCode.INSTANCE, null);
	}

	/**
	 * 成功结果
	 * 
	 * @param data
	 * @return
	 */
	public static <T> Result<T> success(T data) {
	    return new ResultImpl<T>(SuccessCode.INSTANCE, data);
	}
	
	/**
	 * 异常结果
	 * 
	 * @param <T>
	 * @param errorCode
	 * @return
	 */
	public static <T> Result<T> error(ErrorCode errorCode) {
	    return new ResultImpl<T>(errorCode);
    }

	/**
	 * 
	 * @author fengwk
	 */
	@Data
	public static final class ResultImpl<T> implements Result<T> {

		private static final long serialVersionUID = -6520214782056141460L;

		private String code;
		private String message;
		private T data;
		
		ResultImpl(Code code) {
		    this.code = code.getCode();
		    this.message = code.getMessage();
		}
		
		ResultImpl(Code code, T data) {
            this.code = code.getCode();
            this.message = code.getMessage();
            this.data = data;
        }
		
        @Override
        public boolean isSuccess() {
            return SuccessCode.INSTANCE.getCode().equals(code);
        }

	}
	
}
