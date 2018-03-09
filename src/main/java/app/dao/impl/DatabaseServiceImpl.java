/*
 * IBM Confidential
 *
 * OCO Source Materials
 *
 * Copyright IBM Corp. 2018
 *
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has been
 * deposited with the U.S. Copyright Office.
 */
package app.dao.impl;

import app.dao.DatabaseService;
import app.workspace.model.graph.EdgeData;
import app.workspace.model.graph.Elements;
import app.workspace.model.graph.Node;
import app.workspace.model.graph.NodeData;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

import java.util.ArrayList;
import java.util.List;

public class DatabaseServiceImpl implements DatabaseService {

    OrientGraph graph = new OrientGraph("remote:localhost/Workspaces3");

    public DatabaseServiceImpl(OrientGraph graph) {
        this.graph = graph;
    }

    @Override
    public Elements getElements() {
        Elements elements = new Elements();
        List<Node> nodes = new ArrayList<>();
        List<app.workspace.model.graph.Edge> edges = new ArrayList<>();

        Iterable<Vertex> vertexInterable = graph.getVertices("type", "SPACE");
        vertexInterable.forEach(c -> nodes.add(new Node(new NodeData(c.getProperty("workspaceId").toString()))));
        vertexInterable.forEach(c -> c.getEdges(Direction.IN).forEach(d ->
                edges.add(new app.workspace.model.graph.Edge(new EdgeData(d.getVertex(Direction.IN).getProperty("workspaceId").toString(),
                        d.getVertex(Direction.OUT).getProperty("workspaceId").toString())))));

        elements.setNodes(nodes);
        elements.setEdges(edges);
        return elements;
    }
}
