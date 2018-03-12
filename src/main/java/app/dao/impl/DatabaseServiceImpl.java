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
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DatabaseServiceImpl implements DatabaseService {

    OrientGraph graph;

    public DatabaseServiceImpl(OrientGraph graph) {
        this.graph = graph;
    }

    @Override
    public Elements getElements() {
        Elements elements = new Elements();
        List<Node> nodes = new ArrayList<>();
        List<app.workspace.model.graph.Edge> edges = new ArrayList<>();

        Iterable<Vertex> allVertex = graph.getVertices();
        Iterable<Vertex> vertexInterable = graph.getVertices("type", "SPACE");
        allVertex.forEach(c -> nodes.add(new Node(new NodeData(c.getProperty("id").toString()))));
        vertexInterable.forEach(c -> c.getEdges(Direction.IN).forEach(d ->
                edges.add(new app.workspace.model.graph.Edge(new EdgeData(d.getVertex(Direction.IN).getProperty("id").toString(),
                        d.getVertex(Direction.OUT).getProperty("id").toString())))));

        elements.setNodes(nodes);
        elements.setEdges(edges);
        return elements;
    }

    @Override
    public void addSpaceNodeIfNotExists(String spaceId) {
        if (!nodeExistsInGraph(spaceId)) {
            generateSpace(graph, spaceId);
        }
    }

    @Override
    public void addMemberEdgeIfNotExists(String spaceId, String memberId) {
        Edge existingEdge = getEdgeIfExists(spaceId, memberId);
        if (existingEdge == null) {
            Vertex personVertex = personVertex(memberId);
            Vertex spaceVertex = spaceVertex(memberId);
            graph.addEdge(null, personVertex, spaceVertex, "memberOf");
        }
    }

    @Override
    public void removeMemberEdge(String spaceId, String memberId) {
        Edge edgeToRemove = getEdgeIfExists(spaceId, memberId);
        if (edgeToRemove != null) {
            graph.removeEdge(edgeToRemove);
        }
    }

    private Edge getEdgeIfExists(String spaceId, String memberId) {
        Iterator<Edge> spaceEdges = graph.getEdges("id", spaceId).iterator();
        while (spaceEdges.hasNext()) {
            Edge spaceEdge = spaceEdges.next();
            if (spaceEdges.next().getVertex(Direction.OUT).getProperty("id").equals(memberId)) {
                return spaceEdge;
            }
        }
        return null;
    }

    private boolean nodeExistsInGraph(String id) {
        return getNode(id) != null;
    }

    private Vertex getNode(String id) {
        return graph.getVertex(id);
    }

    private Vertex personVertex(String memberId) {
        Vertex personNode = getNode(memberId);
        if (getNode(memberId) != null) {
            return generatePerson(graph, memberId);
        } else {
            return personNode;
        }
    }

    private Vertex spaceVertex(String memberId) {
        Vertex spaceNode = getNode(memberId);
        if (getNode(memberId) != null) {
            return generateSpace(graph, memberId);
        } else {
            return spaceNode;
        }
    }

    private Vertex generatePerson(OrientGraph graph, String id) {
        Vertex person = graph.addVertex(id);
        person.setProperty("type", "PERSON");
        return person;
    }

    private Vertex generateSpace(OrientGraph graph, String id) {
        Vertex space = graph.addVertex(id);
        space.setProperty("type", "SPACE");
        return space;
    }
}
