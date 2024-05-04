package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Bateria extends GameElement implements Item {
	
	public Bateria(Point2D Point2D){
		super(Point2D,"Bateria",1);
	}
	public void consume(Point2D p,GameElement e){
		GameElement g = GameEngine.getInstance().getObjectAtPositionL1(p);
		GameEngine.getInstance().remove(g);
		((Empilhadora)e).bateria();
	}

	@Override
	public boolean isTransposable() {
		return false;
	}
}
