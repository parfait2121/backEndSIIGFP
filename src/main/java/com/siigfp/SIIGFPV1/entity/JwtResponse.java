package com.siigfp.SIIGFPV1.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class JwtResponse {
	private Users users;
	private String jwtToken;	
}
