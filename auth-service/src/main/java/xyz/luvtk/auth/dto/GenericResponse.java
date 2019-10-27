package xyz.luvtk.auth.dto;

import java.io.Serializable;

/**
 * @author Tank Zheng
 * @since 20190918 通用响应参数
 * @param <T> 泛型
 * 通用响应
 */
public class GenericResponse<T> implements Serializable {
	/**
	 * 状态码
	 */
	private int code;
	/**
	 * 是否成功
	 */
	private boolean success;
	/**
	 * 结果
	 */
	private T result;
	/**
	 * 响应信息
	 */
	private String msg;

	public GenericResponse<T> code(int code) {
		this.code = code;
		return this;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public GenericResponse<T> success(boolean success) {
		this.success = success;
		return this;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public GenericResponse<T> result(T result) {
		this.result = result;
		return this;
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public GenericResponse<T> msg(String msg) {
		this.msg = msg;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
