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
package app.dao;

import app.workspace.model.graph.Elements;

public interface DatabaseService {

    Elements getElements();

    void addSpaceNodeIfNotExists(String spaceId);

    void addMemberEdgeIfNotExists(String spaceId, String memberId);

    void removeMemberEdge(String spaceId, String memberId);
}
