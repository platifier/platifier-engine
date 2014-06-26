package lwjgl_game;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;

import org.lwjgl.opengl.PixelFormat;

public class GraphicsEngine
{
	
	private final String WINDOW_TITLE = "Matt Damon";
	private final int WIDTH = 800;
	private final int HEIGHT = 600;
	
	private GraphicsElement element;
	
	private ShaderProgram shader;
	
	public GraphicsEngine()
	{
		initGL();
		initShaders();
		element = new GraphicsElement(shader);
		
	}
	
	private void initGL()
	{
		// Setup an OpenGL context with API version 3.2
		try 
		{
			PixelFormat pixelFormat = new PixelFormat();
			ContextAttribs contextAtrributes = new ContextAttribs(3, 2)
				.withForwardCompatible(true)
				.withProfileCore(true);
			
			Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
			Display.setTitle(WINDOW_TITLE);
			Display.create(pixelFormat, contextAtrributes);
			
			glViewport(0, 0, WIDTH, HEIGHT);
		} 
		catch (LWJGLException e) 
		{
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Setup an XNA like background color
		glClearColor(0.4f, 0.6f, 0.9f, 0f);
		
		// Map the internal OpenGL coordinate system to the entire screen
		glViewport(0, 0, WIDTH, HEIGHT);
		
	}
	
	private void initShaders()
	{
		
		shader = new ShaderProgram("simple");
		
	}
	
	public void draw()
	{
		
		
		element.draw();
	
	}
	
	
}
