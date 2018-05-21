package com.cdt.credito.auth.service.impl;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.Properties;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.imageio.ImageIO;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import com.cdt.credito.auth.model.Captcha;
import com.cdt.credito.auth.service.CaptchaService;
import com.cdt.credito.utils.ByteEncoding;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.util.Config;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Service
public class CaptchaServiceImpl implements CaptchaService {

	private static final int CAPTCHA_ID_BYTES_SIZE = 4;
	private static final String CAPTCHA_CHARS = "abcdefGhijkLmnpqrtuvwxyz2345678";

	private Cache<String, String> captchaValueCache;

	private Properties properties = new Properties();
	private Config config;
	private Producer producer;

	@PostConstruct
	public void init() {
		// Switch off disk based caching.
		ImageIO.setUseCache(false);

		this.properties.put(Constants.KAPTCHA_BORDER, "no");
		this.properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
		this.properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, CAPTCHA_CHARS);

		this.config = new Config(this.properties);

		this.producer = this.config.getProducerImpl();

		this.captchaValueCache = CacheBuilder.newBuilder().maximumSize(100).expireAfterWrite(2, TimeUnit.MINUTES).build();
	}

	@Override
	public String getCaptchaValueById(final String captchaId) {
		return this.captchaValueCache.getIfPresent(captchaId);
	}

	@Override
	public Captcha createCaptcha() throws IOException {
		return createCaptcha(this.createCaptchaText());
	}

	@Override
	synchronized public Captcha createCaptcha(final String text) throws IOException {
		BufferedImage bufferedImage = this.producer.createImage(text);

		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", byteArray);

		String captchaId = newCaptchaId();
		String data = DatatypeConverter.printBase64Binary(byteArray.toByteArray());

		this.captchaValueCache.put(captchaId, text);
		
		return new Captcha(captchaId, text, "data:image/jpeg;base64," + data);
	}

	@Override
	synchronized public String createCaptchaText() {
		String length = "5";

		long time = System.currentTimeMillis();
		if ((time & 1) == 0) {
			length = "6";
		}

		this.properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, length);
		return this.producer.createText();
	}
	
	private String newCaptchaId() {
		String captchaId = this.generateRandomCaptchaId();

		// Prevents captchaId duplication
		ConcurrentMap<String, String> captchaMap = this.captchaValueCache.asMap();
		while (captchaMap.containsKey(captchaId)) {
			captchaId = this.generateRandomCaptchaId();
		}
		return captchaId;
	}

	private String generateRandomCaptchaId() {
        SecureRandom random = new SecureRandom();
        byte[] tokenBytes = new byte[CAPTCHA_ID_BYTES_SIZE];
        random.nextBytes(tokenBytes);

        return ByteEncoding.base62Encode(tokenBytes);
	}

	@Override
	public void invalidateCaptchaById(String captchaId) {
		this.captchaValueCache.invalidate(captchaId);
	}

}
