package lwjgl_game;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

public class GraphicsElement
{

	private ShaderProgram shader;
	
	private int vaoId = 0;
	
	public GraphicsElement(ShaderProgram shader)
	{
		this.shader = shader;
		initGeometry();
	}
	
	public void draw()
	{
		glClear(GL_COLOR_BUFFER_BIT);
		
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		
		glBindVertexArray(vaoId);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		
		if(shader.shaderLoaded())
		{
			ARBShaderObjects.glUseProgramObjectARB(shader.getShader());
		}
		
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		
		if(shader.shaderLoaded())
		{
			ARBShaderObjects.glUseProgramObjectARB(0);
		}
		//GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, vertexCount);
		
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
	
	}
	
	
	private void initGeometry()
	{
		
		final float positions[] = {
			// X Y Z
				-0.5f, 0.5f, 0f,    // v0
				-0.5f, -0.5f, 0f,   // v1
				0.5f, -0.5f, 0f,   // v2
				0.5f, 0.5f, 0f    // v3
		};

		// Vertex colors
		final float colors[] = {
			//  R     G		B
			1.0f, 1.0f, 1.0f,		// White
			0.5f, 0.5f, 0.8f,		// Lightblue
			0.5f, 0.5f, 0.8f,		// Lightblue
			1.0f, 1.0f, 1.0f		// White
		};


		final int indices[] = {
			0,1,3, // Triangle 1
			1,2,3  // Triangle 2
		};
		
		
		float vertices[] = {
			// Left bottom triangle
			-0.5f, 0.5f, 0f,
			-0.5f, -0.5f, 0f,
			0.5f, -0.5f, 0f,
			// Right top triangle
			0.5f, -0.5f, 0f,
			0.5f, 0.5f, 0f,
			-0.5f, 0.5f, 0f
		};
		
		FloatBuffer positionBuffer = BufferUtils.createFloatBuffer(positions.length);
		positionBuffer.put(positions);
		positionBuffer.flip();
		
		int posBuf = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, posBuf);
		glBufferData(GL_ARRAY_BUFFER, positionBuffer, GL_STATIC_DRAW);
		
		
		FloatBuffer colorBuffer = BufferUtils.createFloatBuffer(colors.length);
		colorBuffer.put(colors);
		colorBuffer.flip();
		
		int colBuf = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, colBuf);
		glBufferData(GL_ARRAY_BUFFER, colorBuffer, GL_STATIC_DRAW);
		
		
		IntBuffer indexBuffer = BufferUtils.createIntBuffer(indices.length);
		indexBuffer.put(indices);
		indexBuffer.flip();
		
		int indBuf = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, indBuf);
		glBufferData(GL_ARRAY_BUFFER, indexBuffer, GL_STATIC_DRAW);
		
		
		vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		
		glBindBuffer(GL_ARRAY_BUFFER, posBuf);
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, colBuf);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, indBuf);
		
		glBindVertexArray(0);
		
	}
	
}
