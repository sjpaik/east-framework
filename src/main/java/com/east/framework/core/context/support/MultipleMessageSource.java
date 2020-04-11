package com.east.framework.core.context.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

/**
 * 다중 MessageSource 사용을 위한 MessageSource.
 *
 * Updated on : 2015-09-30 Updated by : love.
 */
public class MultipleMessageSource implements MessageSource {

	private List<MessageSource> messageSources;

	/**
	 * 생성자.
	 */
	public MultipleMessageSource() {
		this.messageSources = new ArrayList<MessageSource>();
	}

	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		for (MessageSource messageSource : messageSources) {
			try {
				String result = messageSource.getMessage(code, args, defaultMessage, locale);
				if (result != null) {
					return result;
				}
			} catch (NoSuchMessageException e) {
				// 메세지를 찾지 못함, 다음 메세지 소스에서 코드 검색.
				continue;
			}
		}
		return null;
	}

	@Override
	public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
		String result = getMessage(code, args, null, locale);
		if (result == null) {
			throw new NoSuchMessageException(code, locale);
		}
		return result;
	}

	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {

		for (MessageSource messageSource : messageSources) {
			try {
				String result = messageSource.getMessage(resolvable, locale);
				if (result != null) {
					return result;
				}
			} catch (NoSuchMessageException e) {
				// 메세지를 찾지 못함, 다음 메세지 소스에서 코드 검색.
				continue;
			}
		}

		/*
		 * 메세지 코드가 없을 경우... 1안. Exception, 2안. code 리턴.
		 */
		String[] codes = resolvable.getCodes();
		throw new NoSuchMessageException(codes.length > 0 ? codes[codes.length - 1] : null, locale);
	}

	/**
	 * @param messageSources
	 *            the messageSources to set
	 */
	public void setMessageSources(List<MessageSource> messageSources) {
		this.messageSources = messageSources;
	}

}