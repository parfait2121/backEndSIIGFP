package com.siigfp.SIIGFPV1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data @NoArgsConstructor @AllArgsConstructor @ToString
public class EmailRequest {
	private String email;
	private int code;
	private String password;

}
