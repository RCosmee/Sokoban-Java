package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class ParedeRachada extends GameElement {
	
	public ParedeRachada(Point2D Point2D){
		super(Point2D,"ParedeRachada",1);
	}
	
	@Override
	public boolean isTransposable() {
		return false;
	}
}
