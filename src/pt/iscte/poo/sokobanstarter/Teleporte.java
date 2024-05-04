package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Teleporte extends GameElement implements Item{
	
	public Teleporte(Point2D Point2D){
		super(Point2D,"Teleporte",1);
	}
	public void consume(Point2D p, GameElement e){
		Point2D position=GameEngine.getInstance().getObjectTeleport(p).getPosition();
		if(GameEngine.getInstance().getMovableAtPosition(position)==null)
			e.setPosition(position);
	}

	@Override
	public boolean isTransposable() {
		return false;
	}
}
