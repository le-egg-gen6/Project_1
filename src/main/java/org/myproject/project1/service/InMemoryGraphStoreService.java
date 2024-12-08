package org.myproject.project1.service;

import lombok.RequiredArgsConstructor;
import org.myproject.project1.config.security.user.UserDetailsImpl;
import org.myproject.project1.core.Graph;
import org.myproject.project1.exception.custom.AuthenticationException;
import org.myproject.project1.utils.SecurityContextUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author nguyenle
 * @since 11:23 PM Tue 11/12/2024
 */
@Service
@RequiredArgsConstructor
public class InMemoryGraphStoreService {

    private final String CACHE_KEY_SEPARATOR = ":";

    private Map<String, Graph> mapId2Graph = new HashMap<>();

    public List<String> getAllUserGraphId() {
        return new ArrayList<>(mapId2Graph.keySet());
    }

    public Graph getGraph(String graphId) {
        return mapId2Graph.get(getCacheKey(graphId));
    }

    public void addGraph(Graph graph) {
        mapId2Graph.put(getCacheKey(graph), graph);
    }

    private String getCacheKey(String graphId) {
//        UserDetailsImpl userDetails = SecurityContextUtils.getCurrentUserDetails();
//        if (userDetails == null) {
//            throw new AuthenticationException("User not logged in");
//        }
//        return userDetails.getId() + CACHE_KEY_SEPARATOR + graphId;
        return graphId;
    }

    private String getCacheKey(Graph graph) {
        return getCacheKey(graph.getId());
    }

}
