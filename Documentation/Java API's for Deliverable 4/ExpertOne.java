// Creating a Java SQL API Interface for the experts to be used for the movies.

public class ExpertOne {
	private String query;
	
	public ExpertOne(){ // Constructor
		this.query = null;	// <- More can be added	
	}
	
	public void init(){
		// TODO code here				
	}
	
	public void update(){ 
		// TODO code here
	}
	
	public Object[] search(Object query){ 
		// TODO code here
		return null;		
	}
	
	public void addItem(Object newObject){ 
		// TODO code here		
	}
	
	public Object removeItem(Object oldObject){ 
		// TODO code here
		return null;
	}	
}