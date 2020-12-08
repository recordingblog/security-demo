package com.zhushan.security.core.properties;

/**
 * 验证码相关配置
 */
public class ValidateCodeProperties {
	
	// 图片
	private ImageCodeProperties image = new ImageCodeProperties();
	
	// 短信
	private SmsCodeProperties sms = new SmsCodeProperties();

	public ImageCodeProperties getImage() {
		return image; 
	}

	public void setImage(ImageCodeProperties image) {
		this.image = image;
	}

	public SmsCodeProperties getSms() {
		return sms;
	}

	public void setSms(SmsCodeProperties sms) {
		this.sms = sms;
	}
	
}
