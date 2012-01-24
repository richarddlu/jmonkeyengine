/**
 * ellipsoidType.java This file was generated by XMLSpy 2006sp2 Enterprise
 * Edition. YOU SHOULD NOT MODIFY THIS FILE, BECAUSE IT WILL BE OVERWRITTEN WHEN
 * YOU RE-RUN CODE GENERATION. Refer to the XMLSpy Documentation for further
 * details. http://www.altova.com/xmlspy
 */

package com.jmex.model.collada.schema;

public class ellipsoidType extends com.jmex.model.collada.xml.Node {

    private static final long serialVersionUID = 1L;

    public ellipsoidType(ellipsoidType node) {
        super(node);
    }

    public ellipsoidType(org.w3c.dom.Node node) {
        super(node);
    }

    public ellipsoidType(org.w3c.dom.Document doc) {
        super(doc);
    }

    public ellipsoidType(com.jmex.model.collada.xml.Document doc,
            String namespaceURI, String prefix, String name) {
        super(doc, namespaceURI, prefix, name);
    }

    public void adjustPrefix() {
        for (org.w3c.dom.Node tmpNode = getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "size"); tmpNode != null; tmpNode = getDomNextChild(
                Element, "http://www.collada.org/2005/11/COLLADASchema",
                "size", tmpNode)) {
            internalAdjustPrefix(tmpNode, true);
        }
    }

    public static int getsizeMinCount() {
        return 1;
    }

    public static int getsizeMaxCount() {
        return 1;
    }

    public int getsizeCount() {
        return getDomChildCount(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "size");
    }

    public boolean hassize() {
        return hasDomChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "size");
    }

    public float3 newsize() {
        return new float3();
    }

    public float3 getsizeAt(int index) throws Exception {
        return new float3(
                getDomNodeValue(dereference(getDomChildAt(Element,
                        "http://www.collada.org/2005/11/COLLADASchema", "size",
                        index))));
    }

    public org.w3c.dom.Node getStartingsizeCursor() throws Exception {
        return getDomFirstChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "size");
    }

    public org.w3c.dom.Node getAdvancedsizeCursor(org.w3c.dom.Node curNode)
            throws Exception {
        return getDomNextChild(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "size", curNode);
    }

    public float3 getsizeValueAtCursor(org.w3c.dom.Node curNode)
            throws Exception {
        if (curNode == null)
            throw new com.jmex.model.collada.xml.XmlException("Out of range");
        else
            return new float3(getDomNodeValue(dereference(curNode)));
    }

    public float3 getsize() throws Exception {
        return getsizeAt(0);
    }

    public void removesizeAt(int index) {
        removeDomChildAt(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "size", index);
    }

    public void removesize() {
        while (hassize())
            removesizeAt(0);
    }

    public void addsize(float3 value) {
        if (value.isNull() == false) {
            appendDomChild(Element,
                    "http://www.collada.org/2005/11/COLLADASchema", "size",
                    value.toString());
        }
    }

    public void addsize(String value) throws Exception {
        addsize(new float3(value));
    }

    public void insertsizeAt(float3 value, int index) {
        insertDomChildAt(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "size", index,
                value.toString());
    }

    public void insertsizeAt(String value, int index) throws Exception {
        insertsizeAt(new float3(value), index);
    }

    public void replacesizeAt(float3 value, int index) {
        replaceDomChildAt(Element,
                "http://www.collada.org/2005/11/COLLADASchema", "size", index,
                value.toString());
    }

    public void replacesizeAt(String value, int index) throws Exception {
        replacesizeAt(new float3(value), index);
    }

    private org.w3c.dom.Node dereference(org.w3c.dom.Node node) {
        return node;
    }
}