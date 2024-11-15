package org.myproject.project1.service;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.core.*;
import org.myproject.project1.core.directed.EdgeDirected;
import org.myproject.project1.core.directed.NodeDirected;
import org.myproject.project1.core.undirected.EdgeUndirected;
import org.myproject.project1.core.undirected.NodeUndirected;
import org.myproject.project1.dto.EdgeDTO;
import org.myproject.project1.dto.PathTraversalDTO;
import org.myproject.project1.shared.AlgorithmConstant;
import org.myproject.project1.shared.NodeDistance;
import org.myproject.project1.utils.GraphUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author nguyenle
 * @since 4:15 PM Tue 11/12/2024
 */
@Service
@RequiredArgsConstructor
public class GraphAlgorithmService {

	private final InMemoryAlgorithmResultCachingService algorithmResultCachingService;

	private final long MAX_ALGO_STEP = 100000000L;

	public PathTraversalDTO getShortestTraversalPath(Graph graph, Node start, Node end) {
		PathTraversalDTO pathTraversalDTO = algorithmResultCachingService.getResult(
			AlgorithmConstant.SHORTEST_PATH,
			graph,
			start,
			end
		);
		if (pathTraversalDTO == null) {
			switch (graph.getType()) {
				case DIRECTED:
					pathTraversalDTO = getShortestTraversalPathForDirectedGraph(graph, (NodeDirected) start, (NodeDirected) end);
					break;
				case UNDIRECTED:
					pathTraversalDTO = getShortestTraversalPathForUndirectedGraph(graph, (NodeUndirected) start, (NodeUndirected) end);
					break;
				default:
					break;
			}
			if (pathTraversalDTO != null) {
				algorithmResultCachingService.storeResult(
						pathTraversalDTO,
						AlgorithmConstant.SHORTEST_PATH,
						graph,
						start,
						end
				);
			}
		}
		return pathTraversalDTO;

	}

	private PathTraversalDTO getShortestTraversalPathForDirectedGraph(Graph graph, NodeDirected start, NodeDirected end) {
		Map<String, NodeDirected> directedNodes = graph.getDirectedNodes();
		Map<String, Edge> edges = graph.getEdges();

		Map<String, Integer> distances = new HashMap<>();
		Map<String, String> paths = new HashMap<>();
		PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>(Comparator.comparing(NodeDistance::getDistance));

		// Initialize distances and add nodes to the priority queue
		for (NodeDirected node : directedNodes.values()) {
			distances.put(node.getId(), Integer.MAX_VALUE);  // Infinity for all nodes initially
			priorityQueue.add(new NodeDistance(node, Integer.MAX_VALUE));
		}

		distances.put(start.getId(), 0);
		priorityQueue.add(new NodeDistance(start, 0));

		long currentStep = 0;

		while (!priorityQueue.isEmpty() && currentStep < MAX_ALGO_STEP) {
			NodeDistance nodeDistance = priorityQueue.poll();
			NodeDirected currentNode = (NodeDirected) nodeDistance.getNode();
			int currentDistance = nodeDistance.getDistance();

			// If we reached the end node, stop and construct the path
			if (currentNode.equals(end)) {
				return GraphUtils.constructPath(graph, start, end, paths);
			}

			for (String edgeId : currentNode.getFromEdges()) {
				EdgeDirected edgeDirected = (EdgeDirected) edges.get(edgeId);
				if (GraphUtils.isRing(currentNode, edgeDirected)) {
					continue;
				}
				// Fetch the neighbor node
				NodeDirected neighborNode = directedNodes.get(edgeDirected.getToId());

				int newDistance = currentDistance + edgeDirected.getWeight();

				if (newDistance < distances.get(neighborNode.getId())) {
					distances.put(neighborNode.getId(), newDistance);
					addPath(paths, currentNode, neighborNode, edgeDirected);
					priorityQueue.add(new NodeDistance(neighborNode, newDistance));
				}
			}

			++currentStep;
		}

		return null;
	}

	private PathTraversalDTO getShortestTraversalPathForUndirectedGraph(Graph graph, NodeUndirected start, NodeUndirected end) {
		Map<String, NodeUndirected> undirectedNodes = graph.getUndirectedNodes();
		Map<String, Edge> edges = graph.getEdges();

		Map<String, Integer> distances = new HashMap<>();
		Map<String, String> paths = new HashMap<>();
		PriorityQueue<NodeDistance> priorityQueue = new PriorityQueue<>(Comparator.comparing(NodeDistance::getDistance));

		// Initialize distances and add nodes to the priority queue
		for (NodeUndirected node : undirectedNodes.values()) {
			distances.put(node.getId(), Integer.MAX_VALUE);  // Infinity for all nodes initially
			priorityQueue.add(new NodeDistance(node, Integer.MAX_VALUE));
		}

		long currentStep = 0;

		while (!priorityQueue.isEmpty() && currentStep < MAX_ALGO_STEP) {
			NodeDistance nodeDistance = priorityQueue.poll();
			NodeUndirected currentNode = (NodeUndirected) nodeDistance.getNode();
			int currentDistance = nodeDistance.getDistance();

			// If we reached the end node, stop and construct the path
			if (currentNode.equals(end)) {
				return GraphUtils.constructPath(graph, start, end, paths);
			}

			for (String edgeId : currentNode.getEdges()) {
				EdgeUndirected edgeUndirected = (EdgeUndirected) edges.get(edgeId);
				String neighborNodeId = edgeUndirected.getNodes().stream()
					.filter(id -> !id.equals(currentNode.getId()))
					.findFirst()
					.orElse(null);
				if (GraphUtils.isRing(currentNode, edgeUndirected)) {
					continue;
				}
				// Fetch the neighbor node
				NodeUndirected neighborNode = undirectedNodes.get(neighborNodeId);

				int newDistance = currentDistance + edgeUndirected.getWeight();

				if (newDistance < distances.get(neighborNode.getId())) {
					distances.put(neighborNode.getId(), newDistance);
					addPath(paths, currentNode, neighborNode, edgeUndirected);
					priorityQueue.add(new NodeDistance(neighborNode, newDistance));
				}
			}

			++currentStep;
		}

		return null;
	}

	public PathTraversalDTO getHamiltonTraversalPath(Graph graph) {
		PathTraversalDTO pathTraversalDTO = algorithmResultCachingService.getResult(
			AlgorithmConstant.HAMILTON_CYCLE,
			graph
		);
		if (pathTraversalDTO == null) {
			switch (graph.getType()) {
				case DIRECTED:
					pathTraversalDTO = getHamiltonTraversalInUndirectedGraph(graph);
					break;
				case UNDIRECTED:
				default:
					break;
			}
			if (pathTraversalDTO != null) {
				algorithmResultCachingService.storeResult(
						pathTraversalDTO,
						AlgorithmConstant.HAMILTON_CYCLE,
						graph
				);
			}
		}
		return pathTraversalDTO;
	}

	public PathTraversalDTO getHamiltonTraversalInUndirectedGraph(Graph graph) {
		if (graph == null || graph.getNodes().isEmpty()) {
			return null;
		}

		PathTraversalDTO result = new PathTraversalDTO();
		result.setType(graph.getType());
		result.setHasPath(false);

		// Get any node as starting point
		String startNodeId = graph.getNodes().keySet().iterator().next();
		NodeUndirected startNode = (NodeUndirected) graph.getNodes().get(startNodeId);

		result.setStart(new NodeDTO(startNodeId));
		result.setEnd(new NodeDTO(startNodeId)); // For Hamilton cycle, start = end

		// Track visited nodes and path
		Set<String> visited = new HashSet<>();
		List<EdgeDTO> path = new ArrayList<>();
		visited.add(startNodeId);

		if (findHamiltonCycle(graph, startNodeId, startNodeId, visited, path, graph.getNodes().size())) {
			result.setHasPath(true);
			result.setPaths(path);
		}

		return result;
	}

	private boolean findHamiltonCycle(
			Graph graph,
			String currentNodeId,
			String startNodeId,
			Set<String> visited,
			List<EdgeDTO> path,
			int totalNodes
	) {
		// If all nodes are visited and we can return to start, we found a cycle
		if (visited.size() == totalNodes) {
			NodeUndirected currentNode = (NodeUndirected) graph.getNodes().get(currentNodeId);

			// Check if there's an edge connecting back to start
			for (String edgeId : currentNode.getEdges()) {
				EdgeUndirected edge = (EdgeUndirected) graph.getEdges().get(edgeId);
				if (edge.getNodes().contains(startNodeId)) {
					path.add(new EdgeDTO(edgeId));
					return true;
				}
			}
			return false;
		}

		// Try all unvisited neighbors
		NodeUndirected currentNode = (NodeUndirected) graph.getNodes().get(currentNodeId);
		for (String edgeId : currentNode.getEdges()) {
			EdgeUndirected edge = (EdgeUndirected) graph.getEdges().get(edgeId);

			// Find the neighbor node through this edge
			String neighborId = edge.getNodes().get(0).equals(currentNodeId) ?
					edge.getNodes().get(1) : edge.getNodes().get(0);

			if (!visited.contains(neighborId)) {
				visited.add(neighborId);
				path.add(new EdgeDTO(edgeId));

				if (findHamiltonCycle(graph, neighborId, startNodeId, visited, path, totalNodes)) {
					return true;
				}

				// Backtrack
				visited.remove(neighborId);
				path.remove(path.size() - 1);
			}
		}

		return false;
	}


	private void addPath(Map<String, String> keyToEdge, Node source, Node target, Edge edge) {
		String key = GraphUtils.getTraversalEdgeKey(source, target);
		keyToEdge.put(key, edge.getId());
	}
}
