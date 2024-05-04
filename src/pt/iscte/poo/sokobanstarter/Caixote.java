package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Caixote extends GameElement implements Movable {
	Alvo alvo;

	public Caixote(Point2D Point2D){
		super(Point2D,"Caixote",3);
	}
	
	public void push(Direction dir){
	Point2D newPosition = getPosition().plus(dir.asVector());
	if (newPosition.getX()>=0 && newPosition.getX()<10 && 
		newPosition.getY()>=0 && newPosition.getY()<10 ){
		setPosition(newPosition);
		}
	arrumado();
	}
	public void arrumado() {
		GameElement a=GameEngine.getInstance().getAlvoAtPosition(getPosition());
		if(a !=null) {
			alvo=((Alvo)a);
			alvo.validar();
		 	//System.out.println("alvo");
		}else{
			if(alvo !=null) {
				alvo.invalidar();
				alvo=null;
			}
		}
	}
	
	@Override
	public boolean isTransposable() {
		return false;
	}
}
