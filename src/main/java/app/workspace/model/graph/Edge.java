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

package app.workspace.model.graph;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Edge {
    private EdgeData data;

    public Edge() {
    }

    public Edge(EdgeData data) {
        this.data = data;
    }

    public EdgeData getData() {
        return data;
    }

    public void setData(EdgeData data) {
        this.data = data;
    }
}
