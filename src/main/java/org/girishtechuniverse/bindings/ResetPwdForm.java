package org.girishtechuniverse.bindings;

import lombok.Data;

@Data
public class ResetPwdForm {

	private Integer userId;

	private String email;

	private String newPwd;

	private String confirmPwd;
}
