package org.myproject.project1.service;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.core.Graph;
import org.myproject.project1.core.Node;
import org.myproject.project1.core.directed.NodeDirected;
import org.myproject.project1.core.undirected.NodeUndirected;
import org.myproject.project1.dto.PathTraversalDTO;
import org.myproject.project1.shared.AlgorithmConstant;
import org.springframework.stereotype.Service;

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

    private PathTraversalDTO getShortestPathDirectedGraph(Graph graph, NodeDirected startNode, NodeDirected endNode) {
        return PathTraversalDTO.notFound(graph);
    }

    private PathTraversalDTO getShortestPathUndirectedGraph(Graph graph, NodeUndirected startNode, NodeUndirected endNode) {
        return PathTraversalDTO.notFound(graph);
    }

}
