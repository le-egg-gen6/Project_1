package org.myproject.project1.service;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.core.Graph;
import org.myproject.project1.core.Node;
import org.myproject.project1.core.directed.EdgeDirected;
import org.myproject.project1.core.directed.NodeDirected;
import org.myproject.project1.core.undirected.EdgeUndirected;
import org.myproject.project1.core.undirected.NodeUndirected;
import org.myproject.project1.dto.PathTraversalDTO;
import org.myproject.project1.shared.AlgorithmConstant;
import org.myproject.project1.shared.GraphType;
import org.myproject.project1.utils.GraphUtils;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author nguyenle
 * @since 10:40 PM Sat 12/7/2024
 */
@Service
@RequiredArgsConstructor
public class AlgorithmService {

    private final InMemoryGraphStoreService graphStoreService;

    private final InMemoryAlgorithmResultCachingService algorithmResultCachingService;

    private final long MAX_ALGORITHM_STEP = 100_000_000L;

    public PathTraversalDTO getShortestPath(String graphId, String nodeStartId, String nodeEndId) {
        Graph graph = graphStoreService.getGraph(graphId);
        if (graph == null) {
            return null;
        }
        Node startNode = graph.getNode(nodeStartId);
        Node endNode = graph.getNode(nodeEndId);
        if (startNode == null || endNode == null) {
            return PathTraversalDTO.notFound(graph);
        }
        PathTraversalDTO result = algorithmResultCachingService.getResult(
                AlgorithmConstant.SHORTEST_PATH,
                graph,
                startNode,
                endNode
        );
        if (result == null) {
            switch (graph.getType()) {
                case DIRECTED:
                    result = getShortestPathDirectedGraph(graph, (NodeDirected) startNode, (NodeDirected) endNode);
                    break;
                case UNDIRECTED:
                    result = getShortestPathUndirectedGraph(graph, (NodeUndirected) startNode, (NodeUndirected) endNode);
                    break;
                default:
                    result = PathTraversalDTO.notFound(graph);
                    break;
            }
            algorithmResultCachingService.storeResult(
                    result,
                    AlgorithmConstant.SHORTEST_PATH,
                    graph,
                    startNode,
                    endNode
            );
        }
        return result;
    }

    // Using Dijkstra's algorithm for shortest path in directed weighted graph
    private PathTraversalDTO getShortestPathDirectedGraph(Graph graph, NodeDirected startNode, NodeDirected endNode) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        Map<String, String> previousEdges = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(
                Comparator.comparingInt(a -> distances.getOrDefault(a, Integer.MAX_VALUE))
        );
        Set<String> visited = new HashSet<>();

        for (String nodeId : graph.getNodes().keySet()) {
            distances.put(nodeId, Integer.MAX_VALUE);
        }
        distances.put(startNode.getId(), 0);
        queue.add(startNode.getId());

        long steps = 0;
        while (!queue.isEmpty() && steps < MAX_ALGORITHM_STEP) {
            String currentNodeId = queue.poll();
            if (currentNodeId.equals(endNode.getId())) {
                break;
            }

            if (visited.contains(currentNodeId)) {
                continue;
            }
            visited.add(currentNodeId);

            NodeDirected currentNode = (NodeDirected) graph.getNode(currentNodeId);
            // Process outgoing edges
            for (String edgeId : currentNode.getSourceEdges()) {
                EdgeDirected edge = (EdgeDirected) graph.getEdge(edgeId);
                String neighborId = edge.getTarget();

                int newDistance = distances.get(currentNodeId) + edge.getWeight();
                if (newDistance < distances.getOrDefault(neighborId, Integer.MAX_VALUE)) {
                    distances.put(neighborId, newDistance);
                    previousNodes.put(neighborId, currentNodeId);
                    previousEdges.put(neighborId, edgeId);
                    queue.add(neighborId);
                }
            }
            steps++;
        }

        boolean hasPath = previousNodes.containsKey(endNode.getId());
        boolean timeLimitExceeded = steps >= MAX_ALGORITHM_STEP;

        if (!hasPath) {
            return PathTraversalDTO.notFound(graph);
        }

        if (timeLimitExceeded) {
            return PathTraversalDTO.timeLimitExceeded(graph);
        }

        return GraphUtils.constructPath(
                graph,
                startNode,
                endNode,
                distances.get(endNode.getId()),
                previousNodes,
                previousEdges
        );
    }

    // Using Dijkstra's algorithm for shortest path in directed weighted graph
    private PathTraversalDTO getShortestPathUndirectedGraph(Graph graph, NodeUndirected startNode, NodeUndirected endNode) {
        Map<String, Integer> distances = new HashMap<>();
        Map<String, String> previousNodes = new HashMap<>();
        Map<String, String> previousEdges = new HashMap<>();
        PriorityQueue<String> queue = new PriorityQueue<>(
                Comparator.comparingInt(a -> distances.getOrDefault(a, Integer.MAX_VALUE))
        );
        Set<String> visited = new HashSet<>();

        for (String nodeId : graph.getNodes().keySet()) {
            distances.put(nodeId, Integer.MAX_VALUE);
        }
        distances.put(startNode.getId(), 0);
        queue.add(startNode.getId());

        long steps = 0;
        while (!queue.isEmpty() && steps < MAX_ALGORITHM_STEP) {
            String currentNodeId = queue.poll();
            if (currentNodeId.equals(endNode.getId())) {
                break;
            }

            if (visited.contains(currentNodeId)) {
                continue;
            }
            visited.add(currentNodeId);

            NodeUndirected currentNode = (NodeUndirected) graph.getNode(currentNodeId);
            for (String edgeId : currentNode.getEdges()) {
                EdgeUndirected edge = (EdgeUndirected) graph.getEdge(edgeId);
                String neighborId = edge.getNodes().get(0).equals(currentNodeId)
                        ? edge.getNodes().get(1)
                        : edge.getNodes().get(0);

                int newDistance = distances.get(currentNodeId) + edge.getWeight();
                if (newDistance < distances.getOrDefault(neighborId, Integer.MAX_VALUE)) {
                    distances.put(neighborId, newDistance);
                    previousNodes.put(neighborId, currentNodeId);
                    previousEdges.put(neighborId, edgeId);
                    queue.add(neighborId);
                }
            }
            steps++;
        }

        boolean hasPath = previousNodes.containsKey(endNode.getId());
        boolean timeLimitExceeded = steps >= MAX_ALGORITHM_STEP;

        if (!hasPath) {
            return PathTraversalDTO.notFound(graph);
        }

        if (timeLimitExceeded) {
            return PathTraversalDTO.timeLimitExceeded(graph);
        }

        return GraphUtils.constructPath(
                graph,
                startNode,
                endNode,
                distances.get(endNode.getId()),
                previousNodes,
                previousEdges
        );
    }

    public PathTraversalDTO getHamiltonCycle(String graphId, String startNodeId) {
        Graph graph = graphStoreService.getGraph(graphId);
        if (graph == null) {
            return null;
        }
        Node startNode = graph.getNode(startNodeId);
        if (graph.getType() == GraphType.DIRECTED || startNode == null) {
            return PathTraversalDTO.notFound(graph);
        }
        PathTraversalDTO result = algorithmResultCachingService.getResult(
                AlgorithmConstant.HAMILTON_CYCLE,
                graph,
                startNode
        );
        if (result == null) {
            result = getHamiltonCycleUndirectedGraph(graph, (NodeUndirected) startNode);
            algorithmResultCachingService.storeResult(
                    result,
                    AlgorithmConstant.HAMILTON_CYCLE,
                    graph,
                    startNode
            );
        }
        return result;
    }

	private PathTraversalDTO getHamiltonCycleUndirectedGraph(Graph graph, NodeUndirected nodeStart) {
		List<String> visitedNodes = new ArrayList<>();
		Set<String> visited = new HashSet<>();
		List<String> usedEdges = new ArrayList<>();
		long[] steps = {0}; // Using array to allow modification in recursive method

		visitedNodes.add(nodeStart.getId());
		visited.add(nodeStart.getId());

		if (findHamiltonCycle(graph, nodeStart, visitedNodes, usedEdges, visited, 1, steps)) {
			return GraphUtils.constructHamiltonPath(graph, usedEdges);
		}

		// Check if we exceeded the step limit
		if (steps[0] >= MAX_ALGORITHM_STEP) {
			return PathTraversalDTO.timeLimitExceeded(graph);
		}

		return PathTraversalDTO.notFound(graph);
	}

	private boolean findHamiltonCycle(Graph graph, NodeUndirected currentNode,
		List<String> visitedNodes, List<String> usedEdges,
		Set<String> visited, int count, long[] steps) {
		if (steps[0] >= MAX_ALGORITHM_STEP) {
			return false;
		}
		steps[0]++;

		if (count == graph.getVertexCount()) {
			NodeUndirected lastNode = (NodeUndirected) graph.getNode(visitedNodes.get(visitedNodes.size() - 1));
			for (String edgeId : lastNode.getEdges()) {
				EdgeUndirected edge = (EdgeUndirected) graph.getEdge(edgeId);
				String neighborId = edge.getNodes().get(0).equals(lastNode.getId())
					? edge.getNodes().get(1)
					: edge.getNodes().get(0);
				if (neighborId.equals(visitedNodes.get(0))) {
					usedEdges.add(edgeId);
					return true;
				}
			}
			return false;
		}

		for (String edgeId : currentNode.getEdges()) {
			EdgeUndirected edge = (EdgeUndirected) graph.getEdge(edgeId);
			String neighborId = edge.getNodes().get(0).equals(currentNode.getId())
				? edge.getNodes().get(1)
				: edge.getNodes().get(0);

			if (!visited.contains(neighborId)) {
				visited.add(neighborId);
				visitedNodes.add(neighborId);
				usedEdges.add(edgeId);

				if (findHamiltonCycle(graph, (NodeUndirected) graph.getNode(neighborId),
					visitedNodes, usedEdges, visited, count + 1, steps)) {
					return true;
				}

				visited.remove(neighborId);
				visitedNodes.remove(visitedNodes.size() - 1);
				usedEdges.remove(usedEdges.size() - 1);
			}
		}

		return false;
	}

}
