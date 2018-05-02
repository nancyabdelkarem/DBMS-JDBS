package eg.edu.alexu.csd.oop.db.cs69.Model.Interpreter;

public class TerminalExpression implements Expression {
	   private final String data;

	   public TerminalExpression(String data){
	      this.data = data;
	   }

	   @Override
	   public boolean interpret(String context) {

	      if(context.contains(data)){
	         return true;
	      }
	      return false;
	   }
	   @Override
	public boolean interpret1(String context) {

		      if(context.startsWith(data)){
		         return true;
		      }
		      return false;
		   }

}
