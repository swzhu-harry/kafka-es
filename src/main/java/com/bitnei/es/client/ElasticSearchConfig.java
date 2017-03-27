package com.bitnei.es.client;

import org.apache.commons.lang.StringUtils;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ElasticSearchConfig {

	static class ClientConfig {
		// ElasticSearch的集群名称
		static String clusterName;
		// 自动嗅探整个集群的状态，把集群中其它机器的ip地址加到客户端中
		static boolean isSniff = true;
		// ElasticSearch的host
		static String nodeHosts;
		// ElasticSearch的索引名称
		static String indexName;
		// ElasticSearch的类型名称
		static String typeName;
	}

	static class BulkProcessorConfig {
		// 缓冲池最大提交文档数（条）
		static int bulkActions = 1000;
		// 缓冲池总文档体积达到多少时提交（MB）
		static int bulkSize = 10;
		// 最大提交间隔（秒）
		static int flushInterval = 10;
		// 并行提交请求数
		static int concurrentRequests = 2;
	}

	static {
		Properties properties = new Properties();
		try {
			properties.load(ElasticSearchConfig.class.getResourceAsStream("/elasticsearch.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (StringUtils.isNotBlank(properties.getProperty("es.cluster.name"))) {
			ClientConfig.clusterName = properties.getProperty("es.cluster.name").trim();
		}
		if (StringUtils.isNotBlank(properties.getProperty("es.node.hosts"))) {
			ClientConfig.nodeHosts = properties.getProperty("es.node.hosts").trim();
		}
		if (StringUtils.isNotBlank(properties.getProperty("es.client.transport.sniff"))) {
			ClientConfig.isSniff = Boolean.valueOf(properties.getProperty("es.client.transport.sniff").trim());
		}
		if (StringUtils.isNotBlank(properties.getProperty("es.index.name"))) {
			ClientConfig.indexName = properties.getProperty("es.index.name").trim();
		}
		if (StringUtils.isNotBlank(properties.getProperty("es.type.name"))) {
			ClientConfig.typeName = properties.getProperty("es.type.name").trim();
		}

		if (StringUtils.isNotBlank(properties.getProperty("es.bulkProcessor.bulkActions"))) {
			BulkProcessorConfig.bulkActions = Integer.valueOf(properties.getProperty("es.bulkProcessor.bulkActions").trim());
		}
		if (StringUtils.isNotBlank(properties.getProperty("es.bulkProcessor.bulkSize"))) {
			BulkProcessorConfig.bulkSize = Integer.valueOf(properties.getProperty("es.bulkProcessor.bulkSize").trim());
		}
		if (StringUtils.isNotBlank(properties.getProperty("es.bulkProcessor.flushInterval"))) {
			BulkProcessorConfig.flushInterval = Integer.valueOf(properties.getProperty("es.bulkProcessor.flushInterval").trim());
		}
		if (StringUtils.isNotBlank(properties.getProperty("es.bulkProcessor.concurrentRequests"))) {
			BulkProcessorConfig.concurrentRequests = Integer.valueOf(properties.getProperty("es.bulkProcessor.concurrentRequests").trim());
		}
	}

	public static String getInfo() {
		List<String> fields = new ArrayList<String>();
		try {
			for (Field f : ClientConfig.class.getDeclaredFields()) {
				fields.add(f.getName() + "=" + f.get(null));
			}
			for (Field f : BulkProcessorConfig.class.getDeclaredFields()) {
				fields.add(f.getName() + "=" + f.get(null));
			}
		} catch (IllegalAccessException ex) {
			ex.printStackTrace();
		}
		return StringUtils.join(fields, ", ");
	}
}
