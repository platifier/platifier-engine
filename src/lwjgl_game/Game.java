package lwjgl_game;

import org.lwjgl.opengl.Display;


public class Game 
{
	public static void main(String[] args) 
	{
		Game displayExample = new Game();
		displayExample.start();
	}
	
	private GraphicsEngine graphicsEngine;
	
	private void start() 
	{
		
		graphicsEngine = new GraphicsEngine();
		
		
		while (!Display.isCloseRequested()) 
		{
			// render OpenGL here 
			graphicsEngine.draw();
			Display.update();
		}
		 
		Display.destroy();
	}
	
	
	
}
