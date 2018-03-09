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

import java.util.List;

public class SocialData {
    private Elements elements;

    public SocialData() {
    }

    public SocialData(Elements elements) {
        this.elements = elements;
    }

    public Elements getElements() {
        return elements;
    }

    public void setElements(Elements elements) {
        this.elements = elements;
    }
}
