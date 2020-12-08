
package com.zhushan.security.core.validate.code.sms;

public class DefaultSmsCodeSender implements SmsCodeSender {

	/* (non-Javadoc)
	 * @see com.zhushan.security.core.validate.code.sms.SmsCodeSender#send(java.lang.String, java.lang.String)
	 */
	@Override
	public void send(String mobile, String code) {
		System.out.println("向手机"+mobile+"发送短信验证码"+code);
	}

}
