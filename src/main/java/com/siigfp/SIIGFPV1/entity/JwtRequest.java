package com.siigfp.SIIGFPV1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class JwtRequest {
	private String userName;
	private String userPassword;
	private int code ;
}
