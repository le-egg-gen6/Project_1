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
                distances,
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
                distances,
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
        if (startNode == null) {
            return PathTraversalDTO.notFound(graph);
        }
        PathTraversalDTO result = algorithmResultCachingService.getResult(
                AlgorithmConstant.HAMILTON_CYCLE,
                graph,
                startNode
        );
        if (result == null) {
            if (graph.getType() == GraphType.UNDIRECTED) {
                result = getHamiltonCycleUndirectedGraph(graph, (NodeUndirected) startNode);
            } else {
                result = PathTraversalDTO.notFound(graph);
            }
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
        return PathTraversalDTO.notFound(graph);
    }

}
