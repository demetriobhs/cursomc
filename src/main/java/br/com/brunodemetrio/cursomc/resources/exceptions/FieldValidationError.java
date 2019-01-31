package br.com.brunodemetrio.cursomc.resources.exceptions;

import java.io.Serializable;

public class FieldValidationError implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String fieldName;
	
	private String msg;
	
	public FieldValidationError() {
	}

	public FieldValidationError(String fieldName, String msg) {
		this.fieldName = fieldName;
		this.msg = msg;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
