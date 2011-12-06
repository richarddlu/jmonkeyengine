/*
 * Copyright (c) 2003-2004, jMonkeyEngine - Mojo Monkey Coding
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

/*
 * Created on 25 janv. 2004
 *
 */
package com.jme.sound.joal;

import com.jme.renderer.Camera;
import com.jme.sound.ISoundRenderer;
import com.jme.sound.scene.SoundSpatial;
import com.jme.sound.scene.SphericalSound;

/**
 * @author Arman Ozcelik
 *
 */
public class SoundRenderer implements ISoundRenderer {

	/* (non-Javadoc)
	 * @see com.jme.sound.ISoundRenderer#setCamera(com.jme.renderer.Camera)
	 */
	public void setCamera(Camera camera) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.jme.sound.ISoundRenderer#getCamera()
	 */
	public Camera getCamera() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.jme.sound.ISoundRenderer#getCamera(int, int)
	 */
	public Camera getCamera(int width, int height) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.jme.sound.ISoundRenderer#draw(com.jme.sound.scene.SoundSpatial)
	 */
	public void draw(SoundSpatial s) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.jme.sound.ISoundRenderer#draw(com.jme.sound.scene.SphericalSound)
	 */
	public void draw(SphericalSound s) {
		// TODO Auto-generated method stub

	}

}