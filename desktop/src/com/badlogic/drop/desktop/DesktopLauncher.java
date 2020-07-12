package com.badlogic.drop.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.drop.dodgefalcon.DodgeFalcon;
import com.badlogic.drop.dropbucket.DropBucket;



public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Drop";
		config.width = 800;
		config.height = 480;
		
		if(arg.length > 0) {
			if(arg[0].equals("-jet")) {
				new LwjglApplication(new DodgeFalcon(), config);
			}	
			else if(arg[0].equals("-bucket")) {
				new LwjglApplication(new DropBucket(), config);
			}	
		}
		else {
			new LwjglApplication(new DropBucket(), config);
		}
	}
}
