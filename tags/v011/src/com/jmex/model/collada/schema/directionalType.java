/**
 * directionalType.java This file was generated by XMLSpy 2006sp2 Enterprise
 * Edition. YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE OVERWRITTEN WHEN
 * YOU RE-RUN CODE GENERATION. Refer to the XMLSpy Documentation for further
 * details. http://www.altova.com/xmlspy
 */

package com.jmex.model.collada.schema;

public class directionalType extends com.jmex.model.collada.xml.Node {

    private static final long serialVersionUID = 1L;

    public directionalType(directionalType node) {
        super(node);
    }

    public directionalType(org.w3c.dom.Node node) {
        super(node);
    }

    public directionalType(org.w3c.dom.Document doc) {
        super(doc);
    }

    public directionalType(com.jmex.model.collada.xml.Document doc,
            String namespaceURI, String prefix, String name) {
        super(doc, namespaceURI, prefix, name);
    }

    public void adjustPrefix() {
        for (org.w3c.dom.Node tmpNode = getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "color"); tmpNode != null; tmpNode = getDomNextChild(
                Element, "http://www.collada.org/2005/11/COLLADASchema",
                "color", tmpNode)) {
            internalAdjustPrefix(tmpNode, true);
            new TargetableFloat3(tmpNode).adjustPrefix();
        }
    }

    public static int getcolorMinCount() {
        return 1;
    }

    public static int getcolorMaxCount() {
        return 1;
    }

    public int getcolorCount() {
        return getDomChildCount(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "color");
    }

    public boolean hascolor() {
        return hasDomChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "color");
    }

    public TargetableFloat3 newcolor() {
        return new TargetableFloat3(domNode.getOwnerDocument().createElementNS(
                "http://www.collada.org/2005/11/COLLADASchema", "color"));
    }

    public TargetableFloat3 getcolorAt(int index) throws Exception {
        return new TargetableFloat3(
                dereference(getDomChildAt(Element,
                        "http://www.collada.org/2005/11/COLLADASchema",
                        "color", index)));
    }

    public org.w3c.dom.Node getStartingcolorCursor() throws Exception {
        return getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "color");
    }

    public org.w3c.dom.Node getAdvancedcolorCursor(org.w3c.dom.Node curNode)
            throws Exception {
        return getDomNextChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "color",
                curNode);
    }

    public TargetableFloat3 getcolorValueAtCursor(org.w3c.dom.Node curNode)
            throws Exception {
        if (curNode == null)
            throw new com.jmex.model.collada.xml.XmlException("Out of range");
        else
            return new TargetableFloat3(dereference(curNode));
    }

    public TargetableFloat3 getcolor() throws Exception {
        return getcolorAt(0);
    }

    public void removecolorAt(int index) {
        removeDomChildAt(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "color", index);
    }

    public void removecolor() {
        while (hascolor())
            removecolorAt(0);
    }

    public void addcolor(TargetableFloat3 value) {
        appendDomElement("http://www.collada.org/2005/11/COLLADASchema",
                "color", value);
    }

    public void insertcolorAt(TargetableFloat3 value, int index) {
        insertDomElementAt("http://www.collada.org/2005/11/COLLADASchema",
                "color", index, value);
    }

    public void replacecolorAt(TargetableFloat3 value, int index) {
        replaceDomElementAt("http://www.collada.org/2005/11/COLLADASchema",
                "color", index, value);
    }

    private org.w3c.dom.Node dereference(org.w3c.dom.Node node) {
        return node;
    }
}