/*
 * Copyright (c) 2003, jMonkeyEngine - Mojo Monkey Coding
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without 
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this 
 * list of conditions and the following disclaimer. 
 * 
 * Redistributions in binary form must reproduce the above copyright notice, 
 * this list of conditions and the following disclaimer in the documentation 
 * and/or other materials provided with the distribution. 
 * 
 * Neither the name of the Mojo Monkey Coding, jME, jMonkey Engine, nor the 
 * names of its contributors may be used to endorse or promote products derived 
 * from this software without specific prior written permission. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE 
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE 
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE 
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR 
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF 
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS 
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN 
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE 
 * POSSIBILITY OF SUCH DAMAGE.
 *
 */

package jme.system;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import jme.exception.MonkeyRuntimeException;
import jme.utility.LoggingSystem;

import org.lwjgl.Display;
import org.lwjgl.DisplayMode;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GLU;

/**
 * <code>DisplaySystem</code> manages the Window and OpenGL context of the 
 * application. There are five parameters that are needed to describe the
 * creation of the window and assciated rendering context. First, the width
 * and height of the window. This can be considered the resolution of the 
 * application. For example: width = 1024, height = 768. BPP describes the
 * depth or bits per pixel of the window. There are two modes supported: 16 and
 * 32. Next, fullscreen describes if the application is in windowed or fullscreen
 * mode. Lastly, title is used to display an application name in the title bar. 
 * The title is not very important if we are rendering in fullscreen mode.
 * 
 * <code>DisplaySystem</code> is a singleton class and cannot be instantiated
 * directly. Use the createDisplaySystem method. You can then used the 
 * getDisplaySystem method to obtain the object. After the DisplaySystem is
 * created, the GL and GLU objects are created and can be retrieved via the 
 * getter methods. 
 * 
 * You can change the attributes of the rendering system dynamically using the 
 * the <code>setAttributes</code> method. This will destroy the current system 
 * and create a new system.
 * 
 * @author Mark Powell
 * @version 0.1.0
 */
public class DisplaySystem {
    //Singleton reference.
    private static DisplaySystem instance = null;

    //Frequency constant
    private static final int DEFAULT_FREQ = 60;

    //the OpenGL objects
    private static GL gl;
    private static GLU glu;

    //attributes of the window
    private int height;
    private int width;
    private int bpp;
    private int freq;
    private boolean fullscreen;
    private String title;

    /**
     * Private constructor. This is called by the createDisplaySystem with the
     * given parameters for the creation of the Display object. The parameters
     * have a few constraints: width and height must be greater than zero, bpp
     * must be either 16, 24 or 32.
     * 
     * @param width the width of the window. Must be greater than 0.
     * @param height the height of the window. Must be greater than 0.
     * @param bpp the color depth of the window. Must be 16 or 32.
     * @param freq the frequency of the monitor.
     * @param fullscreen flag to run fullscreen or not. True is fullscreen, 
     *      false is windowed.
     * @param title the title of the window.
     * @throws MonkeyRuntimeException if width or height is less than or equal 
     *      to 0 or bpp is not 16, 24 or 32.
     */
    private DisplaySystem(
        int width,
        int height,
        int bpp,
        int freq,
        boolean fullscreen,
        String title) {

        //confirm that the parameters are valid.
        if (width <= 0 || height <= 0) {
            throw new MonkeyRuntimeException(
                "Invalid resolution values: " + width + " " + height);
        } else if ((bpp != 32) && (bpp != 16) && (bpp != 24)) {
            throw new MonkeyRuntimeException("Invalid pixel depth: " + bpp);
        }

        //set the window attributes
        this.width = width;
        this.height = height;
        this.bpp = bpp;
        this.freq = freq;
        this.fullscreen = fullscreen;
        this.title = title;

        initDisplay();
    }

    /**
     * <code>setAttributes</code> changes the attributes of the current display.
     * This requires that the current display be destroyed and recreated. The
     * new width and height values must be greater than 0. bpp must be either
     * 16 or 32. It is important to realize that the gl context is being 
     * reinitialized. Therefore, all previous gl states are gone and will have
     * to be reset. For example, textures.
     * 
     * @param width the width of the window. Must be greater than 0.
     * @param height the height of the window. Must be greater than 0.
     * @param bpp the color depth of the display. Must be either 16 or 32.
     * @param freq the frequency of the monitor.
     * @param fullscreen flag to run fullscreen or not. True is fullscreen, 
     *      false is windowed.
     * @param title the title of the window.
     * @throws MonkeyRuntimeException is thrown if width or height is less than
     *      or equal to 0, or bpp is not either 16, 24 or 32.
     */
    public void setAttributes(
        int width,
        int height,
        int bpp,
        int freq,
        boolean fullscreen,
        String title) {
        //check for parameter validity
        if (width < 0 || height < 0) {
            throw new MonkeyRuntimeException(
                "Invalid resolution values: " + width + " " + height);
        } else if ((bpp != 32) && (bpp != 16) && (bpp != 24)) {
            throw new MonkeyRuntimeException("Invalid pixel depth: " + bpp);
        }

        //set the resolution parameters.
        this.width = width;
        this.height = height;
        this.bpp = bpp;
        this.freq = freq;
        this.fullscreen = fullscreen;
        this.title = title;

        //recreate the display
        gl.destroy();
        initDisplay();
        Keyboard.destroy();
        Mouse.destroy();
    }

    /**
     * <code>createDisplaySystem</code> creates a new <code>DisplaySystem</code>
     * which in turns creates the window and OpenGL context. This is a static
     * method used to initialize the singleton <code>DisplaySystem</code> object.
     * After the <code>DisplaySystem</code> has been initialized via this method
     * call, <code>getDisplaySystem</code> can be used to retrieve the object
     * reference.
     * 
     * If the <code>DisplaySystem</code> has already been created, a second
     * call to <code>createDisplaySystem</code> is ignored. Use the setter 
     * methods to alter the attributes of the display.
     * 
     * @param width the width of the window. Must be greater than 0.
     * @param height the height of the window. Must be greater than 0.
     * @param bpp the color depth of the window. Must be 16 or 32.
     * @param freq the frequency of the monitor.
     * @param fullscreen flag to run fullscreen or not. True is fullscreen, 
     *      false is windowed.
     * @param title the title of the window.
     * @throws MonkeyRuntimeException if width or height is less than or equal 
     *      to 0 or bpp is not 16, 24 or 32.
     * 
     */
    public static void createDisplaySystem(
        int width,
        int height,
        int bpp,
        int freq,
        boolean fullscreen,
        String title) {

        //Only create a new DisplaySystem if there is no system in existance.
        if (null == instance) {
            instance = new DisplaySystem(width, height, bpp, freq, fullscreen, 
                            title);
        }
    }

    /**
     * <code>createDisplaySystem</code> creates a new <code>DisplaySystem</code>
     * which in turns creates the window and OpenGL context. This is a static
     * method used to initialize the singleton <code>DisplaySystem</code> object.
     * After the <code>DisplaySystem</code> has been initialized via this method
     * call, <code>getDisplaySystem</code> can be used to retrieve the object
     * reference.
     * 
     * If the <code>DisplaySystem</code> has already been created, a second
     * call to <code>createDisplaySystem</code> is ignored. Use the setter 
     * methods to alter the attributes of the display.
     * 
     * No display properties are passed, as this method makes use of the 
     * <code>PropertiesIO</code> class to obtain needed information from
     * the properties file, if the file does not exist a dialog will appear
     * for the user to check them. A flag can also be set to always display
     * this dialog.
     * 
     * @param title the title of the window.
     * @param imageFile the image to use for the properties dialog.
     * @param always a flag declaring how often the properties dialog should
     *      be displayed. True will display the dialog every time, false will
     *      display the dialog only when the properties file is missing.
     */
    public static void createDisplaySystem(String title, String imageFile, 
            boolean always) {
                
        PropertiesIO prop = new PropertiesIO("properties.cfg");

        if (!prop.load() || always) {

            PropertiesDialog dialog = new PropertiesDialog(prop, imageFile);

            while (!dialog.isDone()) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    LoggingSystem.getLoggingSystem().getLogger().log(
                            Level.WARNING,
                            "Error waiting for dialog system, using defaults.");
                }
            }
        }

        if (null == instance) {
            instance =
                new DisplaySystem(
                    prop.getWidth(),
                    prop.getHeight(),
                    prop.getDepth(),
                    prop.getFreq(),
                    prop.getFullscreen(),
                    title);
        }
    }

    /**
     * <code>getGL</code> returns the OpenGL core object. This <code>GL</code>
     * object can be used to make any calls to the OpenGL core gl functions.
     * 
     * @return the <code>GL</code> object referred to this display. If 
     *      <code>createDisplaySystem</code> has not been called before invoking
     *      this method, null will be returned.
     */
    public GL getGL() {
        return gl;
    }

    /**
     * <code>getGLU</code> returns the OpenGL utility object. This <code>GLU</code>
     * object can be used to make any calls to the OpenGL utility functions.
     * 
     * @return the <code>GLU</code> object referred to this display. If 
     *      <code>createDisplaySystem</code> has not been called before invoking
     *      this method, null will be returned.
     */
    public GLU getGLU() {
        return glu;
    }

    /**
     * <code>getDisplaySystem</code> returns the reference to the singleton 
     * object of the <code>DisplaySystem</code>. After a call to 
     * <code>createDisplaySystem</code> is made, you can retrieve any 
     * display system information using this object.
     * 
     * @return the singleton instance of the <code>DisplaySystem</code>. If 
     *      <code>createDisplaySystem</code> has not been called before invoking
     *      this method, null will be returned.
     */
    public static DisplaySystem getDisplaySystem() {
        return instance;
    }

    /**
     * <code>getValidDisplayMode</code> returns a <code>DisplayMode</code> object
     * that has the requested width, height and color depth. If there is no
     * mode that supports a requested resolution, null is returned.
     * 
     * @param width the width of the desired mode.
     * @param height the height of the desired mode.
     * @param bpp the color depth of the desired mode.
     * @param freq the frequency of the monitor.
     * @return <code>DisplayMode</code> object that supports the requested
     *      resolutions. Null is returned if no valid modes are found.
     */
    public DisplayMode getValidDisplayMode(int width, int height, int bpp, 
                int freq) {
        //get all the modes, and find one that matches our width, height, bpp.
        DisplayMode[] modes = Display.getAvailableDisplayModes();
        //Make sure that we find the mode that uses our current monitor freq.
        GraphicsEnvironment ge =
            GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice[] gd = ge.getScreenDevices();
        for (int i = 0; i < modes.length; i++) {
        	if (modes[i].width == width
                && modes[i].height == height
                && modes[i].bpp == bpp
                && modes[i].freq == freq) {

                LoggingSystem.getLoggingSystem().getLogger().log(
                    Level.INFO,
                    "Setting mode to " + modes[i]);
                return modes[i];
            }
        }

        //none found
        return null;
    }

    /**
     * <code>cullMode</code> sets the culling mode. 
     * 
     * @param mode the mode to cull.
     * @param on true turn on culling, false turn it off.
     */
    public void cullMode(int mode, boolean on) {
        gl.cullFace(mode);
        if (on) {
            gl.enable(GL.CULL_FACE);
        } else {
            gl.disable(GL.CULL_FACE);
        }
    }

    /**
     * <code>takeScreenShot</code> writes the screen pixels to a png image file.
     * The client is required to supply the method with the name of the file.
     * This can include subdirectories. Do not supply a file extension, as
     * .png will be appended to it. For example: "screenshot" will create a 
     * file of screenshot.png.
     * 
     * @param filename the filename to save the screenshot as.
     * @return true if the screenshot was successfully captured, false if it
     *      wasn't.
     * @throws MonkeyRuntimeException if the filename is null.
     */
    public boolean takeScreenShot(String filename) {
    	
    	if (null == filename) {
            throw new MonkeyRuntimeException("Screenshot filename cannot be null");
        }
        LoggingSystem.getLoggingSystem().getLogger().log(
            Level.INFO,
            "Taking screenshot: " + filename + ".png");

        //Create a pointer to the image info and create a buffered image to
        //hold it.
        IntBuffer buff =
            ByteBuffer
                .allocateDirect(width * height * 4)
                .order(ByteOrder.nativeOrder())
                .asIntBuffer();
        int buffh = Sys.getDirectBufferAddress(buff);
        gl.readPixels(
            0,
            0,
            width,
            height,
            GL.BGRA,
            GL.UNSIGNED_BYTE,
            buffh);
        BufferedImage img =
            new BufferedImage(
                width,
                height,
                BufferedImage.TYPE_INT_RGB);

        //Grab each pixel information and set it to the BufferedImage info.
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                img.setRGB(
                    x,
                    y,
                    buff.get(
                        (height - y - 1) * width
                            + x));
            }
        }

        //write out the screenshot image to a file.
        try {
            File out = new File(filename + ".png");
            return ImageIO.write(img, "png", out);
        } catch (IOException e) {
            LoggingSystem.getLoggingSystem().getLogger().log(
                Level.WARNING,
                "Could not create file: " + filename + ".png");
            return false;
        }
    }

    /**
     * <code>initDisplay</code> initializes the <code>Display</code> object
     * using a valid display mode. 
     * 
     * @throws MonkeyRuntimeException if no valid display mode is found for
     *      the display's request resolutions.
     */
    private void initDisplay() {

        try {
            //create the Display. 
            DisplayMode mode = getValidDisplayMode(width, height, bpp, freq);
            if (null == mode) {
                throw new MonkeyRuntimeException("Bad display mode");
            }

            if (fullscreen) {
                Display.setDisplayMode(mode);
                gl = new GL(title, bpp, 0, 1, 0);
            } else {
                int x, y;
                x =
                    (Toolkit.getDefaultToolkit().getScreenSize().width - width)
                        / 2;
                y =
                    (Toolkit.getDefaultToolkit().getScreenSize().height
                        - height)
                        / 2;
                gl = new GL(title, x, y, width, height, bpp, 0, 1, 0);
            }

            gl.create();
            gl.determineAvailableExtensions();

            LoggingSystem.getLoggingSystem().getLogger().log(
                Level.INFO,
                "Created display.");
        } catch (Exception e) {
            LoggingSystem.getLoggingSystem().getLogger().log(
                Level.SEVERE,
                "Failed to create display due to " + e);
            System.exit(1);
        }

        glu = new GLU(gl);
    }
}
