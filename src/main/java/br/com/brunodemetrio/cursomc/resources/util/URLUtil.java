package br.com.brunodemetrio.cursomc.resources.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class URLUtil {
	
	public static String decodeURLParam(String param) {
		try {
			return URLDecoder.decode(param, "URF-8");
		} catch (UnsupportedEncodingException e) {
			return "";
		}
	}
	
	public static List<Integer> parseIntegerList(String integerList, String regex) {
		if (integerList != null && !"".equals(integerList)) {
			return Arrays.asList(integerList.split(regex)).stream()
					.map(integer -> Integer.parseInt(integer))
					.collect(Collectors.toList());
		}
		
		return Collections.emptyList();
	}

}
