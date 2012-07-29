/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jme3.gde.scenecomposer.tools;

import com.jme3.asset.AssetManager;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.gde.core.sceneexplorer.nodes.JmeNode;
import com.jme3.gde.core.sceneexplorer.nodes.JmeSpatial;
import com.jme3.gde.core.undoredo.AbstractUndoableSceneEdit;
import com.jme3.gde.scenecomposer.SceneComposerToolController;
import com.jme3.gde.scenecomposer.SceneEditTool;
import com.jme3.material.Material;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Quad;
import org.openide.loaders.DataObject;

/**
 * Move an object.
 * When created, it generates a quad that will lie along a plane
 * that the user selects for moving on. When the mouse is over
 * the axisMarker, it will highlight the plane that it is over: XY,XZ,YZ.
 * When clicked and then dragged, the selected object will move along that 
 * plane.
 * @author Brent Owens
 */
public class MoveTool extends SceneEditTool {

    private Vector3f pickedPlane;
    private Vector3f startLoc = new Vector3f();
    private Vector3f lastLoc = new Vector3f();
    private boolean wasDragging = false;
    private Vector3f offset;
    private Node plane;
    Material pinkMat;
    private final Quaternion XY = new Quaternion().fromAngleAxis(0, new Vector3f(1, 0, 0));
    private final Quaternion YZ = new Quaternion().fromAngleAxis(-FastMath.PI / 2, new Vector3f(0, 1, 0));
    private final Quaternion XZ = new Quaternion().fromAngleAxis(FastMath.PI / 2, new Vector3f(1, 0, 0));
    //temp vars 
    private Quaternion rot = new Quaternion();
    private Vector3f newPos = new Vector3f();

    public MoveTool() {
        axisPickType = AxisMarkerPickType.axisAndPlane;
        setOverrideCameraControl(true);

        float size = 1000;
        Geometry g = new Geometry("plane", new Quad(size, size));
        g.setLocalTranslation(-size / 2, -size / 2, 0);
        plane = new Node();
        plane.attachChild(g);


    }

    @Override
    public void activate(AssetManager manager, Node toolNode, Node onTopToolNode, Spatial selectedSpatial, SceneComposerToolController toolController) {
        super.activate(manager, toolNode, onTopToolNode, selectedSpatial, toolController);
        displayPlanes();
    }

    @Override
    public void actionPrimary(Vector2f screenCoord, boolean pressed, JmeNode rootNode, DataObject dataObject) {
        if (!pressed) {
            setDefaultAxisMarkerColors();
            pickedPlane = null; // mouse released, reset selection
            offset = null;
            if (wasDragging) {
                actionPerformed(new MoveUndo(toolController.getSelectedSpatial(), startLoc, lastLoc));
                wasDragging = false;
            }
        }
    }

    @Override
    public void actionSecondary(Vector2f screenCoord, boolean pressed, JmeNode rootNode, DataObject dataObject) {
    }

    @Override
    public void mouseMoved(Vector2f screenCoord, JmeNode rootNode, DataObject currentDataObject, JmeSpatial selectedSpatial) {
        if (pickedPlane == null) {
            highlightAxisMarker(camera, screenCoord, axisPickType);
        } else {
            pickedPlane = null;
            offset = null;
        }
    }

    @Override
    public void draggedPrimary(Vector2f screenCoord, boolean pressed, JmeNode rootNode, DataObject currentDataObject) {
        if (!pressed) {
            setDefaultAxisMarkerColors();
            pickedPlane = null; // mouse released, reset selection
            offset = null;
            if (wasDragging) {
                actionPerformed(new MoveUndo(toolController.getSelectedSpatial(), startLoc, lastLoc));
                wasDragging = false;
            }
            return;
        }

        if (toolController.getSelectedSpatial() == null) {
            return;
        }

        if (pickedPlane == null) {
            pickedPlane = pickAxisMarker(camera, screenCoord, axisPickType);
            if (pickedPlane == null) {
                return;
            }
            startLoc = toolController.getSelectedSpatial().getLocalTranslation().clone();
            rot = rot.set(toolController.getSelectedSpatial().getWorldRotation());
            if (pickedPlane.equals(new Vector3f(1, 1, 0))) {
                plane.setLocalRotation(rot.multLocal(XY));
            } else if (pickedPlane.equals(new Vector3f(1, 0, 1))) {
                plane.setLocalRotation(rot.multLocal(XZ));
            } else if (pickedPlane.equals(new Vector3f(0, 1, 1))) {
                plane.setLocalRotation(rot.multLocal(YZ));
            }
            plane.setLocalTranslation(startLoc);
        }

        Vector3f planeHit = pickWorldLocation(camera, screenCoord, plane, null);
        if (planeHit == null) {
            return;
        }

        Spatial selected = toolController.getSelectedSpatial();
        Spatial parent = selected.getParent();


        if (parent == null) {
            //we are moving the root node, move is computed in local translation
            if (offset == null) {
                offset = planeHit.subtract(startLoc); // get the offset when we start so it doesn't jump
            }

            newPos.set(planeHit).subtractLocal(offset);
            lastLoc.set(newPos);
            selected.setLocalTranslation(newPos);
        } else {

            //offset in world space
            if (offset == null) {
                offset = planeHit.subtract(selected.getWorldTranslation()); // get the offset when we start so it doesn't jump
            }

            newPos = planeHit.subtract(offset);

            //computing the inverse world transform to get the new localtranslation
            newPos.subtractLocal(selected.getParent().getWorldTranslation());
            newPos = selected.getParent().getWorldRotation().inverse().normalizeLocal().multLocal(newPos);
            newPos.divideLocal(selected.getWorldScale());
            selected.setLocalTranslation(newPos);
            lastLoc.set(newPos);
        }

        RigidBodyControl control = toolController.getSelectedSpatial().getControl(RigidBodyControl.class);
        if (control != null) {
            control.setPhysicsLocation(toolController.getSelectedSpatial().getWorldTranslation());
        }
        CharacterControl character = toolController.getSelectedSpatial().getControl(CharacterControl.class);
        if (character != null) {
            character.setPhysicsLocation(toolController.getSelectedSpatial().getWorldTranslation());
        }
        updateToolsTransformation();

        wasDragging = true;
    }

    @Override
    public void draggedSecondary(Vector2f screenCoord, boolean pressed, JmeNode rootNode, DataObject currentDataObject) {
    }

    private class MoveUndo extends AbstractUndoableSceneEdit {

        private Spatial spatial;
        private Vector3f before, after;

        MoveUndo(Spatial spatial, Vector3f before, Vector3f after) {
            this.spatial = spatial;
            this.before = before;
            this.after = after;
        }

        @Override
        public void sceneUndo() {
            spatial.setLocalTranslation(before);
            RigidBodyControl control = toolController.getSelectedSpatial().getControl(RigidBodyControl.class);
            if (control != null) {
                control.setPhysicsLocation(toolController.getSelectedSpatial().getWorldTranslation());
            }
            CharacterControl character = toolController.getSelectedSpatial().getControl(CharacterControl.class);
            if (character != null) {
                character.setPhysicsLocation(toolController.getSelectedSpatial().getWorldTranslation());
            }
            toolController.selectedSpatialTransformed();
        }

        @Override
        public void sceneRedo() {
            spatial.setLocalTranslation(after);
            RigidBodyControl control = toolController.getSelectedSpatial().getControl(RigidBodyControl.class);
            if (control != null) {
                control.setPhysicsLocation(toolController.getSelectedSpatial().getWorldTranslation());
            }
            CharacterControl character = toolController.getSelectedSpatial().getControl(CharacterControl.class);
            if (character != null) {
                character.setPhysicsLocation(toolController.getSelectedSpatial().getWorldTranslation());
            }
            toolController.selectedSpatialTransformed();
        }
    }
}
