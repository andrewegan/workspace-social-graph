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
public class Node {
    private NodeData data;

    public Node() {
    }

    public Node(NodeData data) {
        this.data = data;
    }

    public NodeData getData() {
        return data;
    }

    public void setData(NodeData data) {
        this.data = data;
    }
}
