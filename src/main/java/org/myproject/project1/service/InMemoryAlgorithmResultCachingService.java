package org.myproject.project1.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import org.myproject.project1.core.Graph;
import org.myproject.project1.core.Node;
import org.myproject.project1.dto.PathTraversalDTO;
import org.myproject.project1.shared.AlgorithmConstant;
import org.springframework.stereotype.Service;

/**
 * @author nguyenle
 * @since 10:38 AM Thu 11/14/2024
 */
@Service
public class InMemoryAlgorithmResultCachingService {

	private final String CACHE_KEY_SEPARATOR = ":";

	private Cache<String, PathTraversalDTO> cacheToResult = Caffeine.newBuilder()
		.expireAfterWrite(30, TimeUnit.MINUTES)
		.maximumSize(1_000)
		.build();

	public String getCacheKey(AlgorithmConstant algorithm, Graph graph, Node... nodes) {
		StringBuilder sb = new StringBuilder();
		sb.append(algorithm.getName()).append(CACHE_KEY_SEPARATOR).append(graph.getUniqueHash());
		for (Node node : nodes) {
			sb.append(CACHE_KEY_SEPARATOR).append(node.getId());
		}
		return sb.toString();
	}

	public void storeResult(PathTraversalDTO result, AlgorithmConstant algorithm, Graph graph, Node... nodes) {
		cacheToResult.put(getCacheKey(algorithm, graph, nodes), result);
	}

	public PathTraversalDTO getResult(AlgorithmConstant algorithm, Graph graph, Node... nodes) {
		return cacheToResult.getIfPresent(algorithm.getName());
	}

}
