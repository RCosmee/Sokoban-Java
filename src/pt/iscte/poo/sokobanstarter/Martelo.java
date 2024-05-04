package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Martelo extends GameElement implements Item{
	
	public Martelo(Point2D Point2D){
		super(Point2D,"Martelo",1);
	}
	public void consume(Point2D p,GameElement e){
		GameElement g = GameEngine.getInstance().getObjectAtPositionL1(p);
		GameEngine.getInstance().remove(g);
		((Empilhadora)e).martelo();
	}

	@Override
	public boolean isTransposable() {
		return false;
	}
}
