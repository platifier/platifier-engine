package lwjgl_game;

import static org.lwjgl.opengl.GL11.GL_FALSE;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.lwjgl.opengl.ARBFragmentShader;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexShader;

public class ShaderProgram
{
	
	private int program = 0;
	private String name;
	private boolean shaderLoaded = false;
	
	public ShaderProgram(String name)
	{
		this.name = name;
		createShaderProgram();
	}
	
	public String getName()
	{
		return name;
	}
	
	public boolean shaderLoaded()
	{
		return shaderLoaded;
	}
	
	public int getShader()
	{
		return program;
	}
	
	
	private void createShaderProgram()
	{
		
		int vertShader = 0, fragShader = 0;
    	
    	try {
            vertShader = createShader("src/"+name+".vert",ARBVertexShader.GL_VERTEX_SHADER_ARB);
            fragShader = createShader("src/"+name+".frag",ARBFragmentShader.GL_FRAGMENT_SHADER_ARB);
    	}
    	catch(Exception exc) 
    	{
    		exc.printStackTrace();
    		return;
    	}
    	finally 
    	{
    		if(vertShader == 0 || fragShader == 0)
    			return;
    	}
    	
    	program = ARBShaderObjects.glCreateProgramObjectARB();
    	
    	if(program == 0)
    	{
    		return;
    	}
    	
    	ARBShaderObjects.glAttachObjectARB(program, vertShader);
        ARBShaderObjects.glAttachObjectARB(program, fragShader);
        
        ARBShaderObjects.glLinkProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_LINK_STATUS_ARB) == GL_FALSE) {
            System.err.println(getLogInfo(program));
            return;
        }
        
        ARBShaderObjects.glValidateProgramARB(program);
        if (ARBShaderObjects.glGetObjectParameteriARB(program, ARBShaderObjects.GL_OBJECT_VALIDATE_STATUS_ARB) == GL_FALSE) {
        	System.err.println(getLogInfo(program));
        	return;
        }
        
        shaderLoaded = true;
		
	}
	
	
	
	private int createShader(String filename, int shaderType) throws Exception {
    	int shader = 0;
    	try {
	        shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
	        
	        if(shader == 0)
	        	return 0;
	        
	        ARBShaderObjects.glShaderSourceARB(shader, readFileAsString(filename));
	        ARBShaderObjects.glCompileShaderARB(shader);
	        
	        if (ARBShaderObjects.glGetObjectParameteriARB(shader, ARBShaderObjects.GL_OBJECT_COMPILE_STATUS_ARB) == GL_FALSE)
	            throw new RuntimeException("Error creating shader: " + getLogInfo(shader));
	        
	        return shader;
    	}
    	catch(Exception exc) {
    		ARBShaderObjects.glDeleteObjectARB(shader);
    		throw exc;
    	}
    }
	
	private static String getLogInfo(int obj) 
	{
        return ARBShaderObjects.glGetInfoLogARB(obj, ARBShaderObjects.glGetObjectParameteriARB(obj, ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB));
    }
	
	private String readFileAsString(String filename) throws Exception {
        StringBuilder source = new StringBuilder();
        
        FileInputStream in = new FileInputStream(filename);
        
        Exception exception = null;
        
        BufferedReader reader;
        try{
            reader = new BufferedReader(new InputStreamReader(in,"UTF-8"));
            
            Exception innerExc= null;
            try {
            	String line;
                while((line = reader.readLine()) != null)
                    source.append(line).append('\n');
            }
            catch(Exception exc) {
            	exception = exc;
            }
            finally {
            	try {
            		reader.close();
            	}
            	catch(Exception exc) {
            		if(innerExc == null)
            			innerExc = exc;
            		else
            			exc.printStackTrace();
            	}
            }
            
            if(innerExc != null)
            	throw innerExc;
        }
        catch(Exception exc) {
        	exception = exc;
        }
        finally {
        	try {
        		in.close();
        	}
        	catch(Exception exc) {
        		if(exception == null)
        			exception = exc;
        		else
					exc.printStackTrace();
        	}
        	
        	if(exception != null)
        		throw exception;
        }
        
        return source.toString();
    }
}
