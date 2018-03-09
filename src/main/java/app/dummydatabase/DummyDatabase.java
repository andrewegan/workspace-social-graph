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
package app.dummydatabase;

import app.workspace.model.graph.EdgeData;
import app.workspace.model.graph.Elements;
import app.workspace.model.graph.Node;
import app.workspace.model.graph.NodeData;
import com.orientechnologies.orient.client.remote.OServerAdmin;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DummyDatabase {

    OrientGraph graph;

    public DummyDatabase(OrientGraph graph) {
        this.graph = graph;
    }

    /**
     *         OrientGraph graph = new OrientGraph("remote:localhost/Workspaces3");
     DatabaseService dummyDatabase = new DatabaseServiceImpl(graph);
     System.out.println(dummyDatabase.getElements());
     * @return
     */
    public Elements getData() {

        Elements elements = new Elements();

        List<app.workspace.model.graph.Node> nodes = new ArrayList<>();

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

    public void initDb() throws IOException {
        try{
            createDummyData(graph);
        } finally {
            graph.shutdown();
        }
    }

    private void createDummyData(OrientGraph graph) {
        List<Vertex> spaceVertices = new ArrayList<>();
        for (int i = 1; i < 4; i++) {
            spaceVertices.add(generateSpace(graph, Integer.toString(i), "S" + i));
        }
        List<Vertex> peopleVertices = new ArrayList<>();
        for (int i = 1; i < 7; i++) {
            peopleVertices.add(generatePerson(graph, Integer.toString(i), "P" + i, "p" + i + "@example.com"));
        }


        Edge space1Member1 = graph.addEdge("s1m1", peopleVertices.get(0), spaceVertices.get(0), "memberOf");
        Edge space1Member2 = graph.addEdge("s1m2", peopleVertices.get(1), spaceVertices.get(0), "memberOf");
        Edge space1Member3 = graph.addEdge("s1m3", peopleVertices.get(2), spaceVertices.get(0), "memberOf");
        Edge space1Member4 = graph.addEdge("s1m4", peopleVertices.get(3), spaceVertices.get(0), "memberOf");
        Edge space1Member5 = graph.addEdge("s1m5", peopleVertices.get(4), spaceVertices.get(0), "memberOf");

        Edge space2Member1 = graph.addEdge("s2m1", peopleVertices.get(0), spaceVertices.get(1), "memberOf");
        Edge space2Member2 = graph.addEdge("s2m2", peopleVertices.get(1), spaceVertices.get(1), "memberOf");
        Edge space2Member3 = graph.addEdge("s2m3", peopleVertices.get(2), spaceVertices.get(1), "memberOf");
        Edge space2Member4 = graph.addEdge("s2m4", peopleVertices.get(5), spaceVertices.get(1), "memberOf");

        Edge space3Member1 = graph.addEdge("s3m1", peopleVertices.get(1), spaceVertices.get(2), "memberOf");
        Edge space3Member2 = graph.addEdge("s3m2", peopleVertices.get(4), spaceVertices.get(2), "memberOf");
    }

    private Vertex generatePerson(OrientGraph graph, String id, String name, String email) {
        Vertex person = graph.addVertex(id); // 1st OPERATION: IMPLICITLY BEGINS TRANSACTION
        person.setProperty( "name", name );
        person.setProperty( "email", email);
        person.setProperty( "workspaceId", UUID.randomUUID().toString());
        person.setProperty("type", "PERSON");
        return person;
    }

    private Vertex generateSpace(OrientGraph graph, String id, String name) {
        Vertex space = graph.addVertex(id); // 1st OPERATION: IMPLICITLY BEGINS TRANSACTION
        space.setProperty( "name", name );
        space.setProperty( "workspaceId", UUID.randomUUID().toString());
        space.setProperty("type", "SPACE");
        return space;
    }

}
