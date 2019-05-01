package com.kgc.tangcco.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Member implements Serializable {
	private String email;
	private String username;
	private String password;
	private Date regdate;
	private Date lastdate;
	private String photo;
	private String phone;
	private Integer status;
	private Integer flag;
	private String license;
	private String ip;
	private String code; // 表示营业执照
}
