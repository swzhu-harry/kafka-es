package com.bitnei.es.builder;

import org.apache.commons.lang.time.FastDateFormat;

import com.bitnei.es.client.ElasticSearchConfig.ClientConfig;

public class IndexNameBuilder {

	private static FastDateFormat fastDateFormat = FastDateFormat.getInstance(ClientConfig.indexPostfixFormat);

	public static String getIndexName(long timeStamp) {
		String indexName = new StringBuilder(ClientConfig.indexPrefix).append('-')
				.append(fastDateFormat.format(timeStamp)).toString();

		return indexName;
	}
}
