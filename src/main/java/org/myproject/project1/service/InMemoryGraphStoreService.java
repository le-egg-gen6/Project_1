package org.myproject.project1.service;

import org.myproject.project1.core.Graph;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nguyenle
 * @since 11:23 PM Tue 11/12/2024
 */
@Service
public class InMemoryGraphStoreService {

    private Map<String, Graph> mapId2Graph = new HashMap<>();

    public List<Graph> getAllGraphs() {
        return mapId2Graph.values().stream().toList();
    }

    public Graph getGraph(String id) {
        return mapId2Graph.get(id);
    }

    public void addGraph(Graph graph) {
        addGraph(graph.getId(), graph);
    }

    private void addGraph(String id, Graph graph) {
        mapId2Graph.put(id, graph);
    }

}
