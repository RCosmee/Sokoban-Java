package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;

public class Alvo extends GameElement {
	private boolean Valido;
	
	public Alvo(Point2D Point2D){
		super(Point2D,"Alvo",1);
	}
	
	public void validar() {
		Valido=true;
	}
	
	public void invalidar() {
		Valido=false;
	}
	public boolean getValido() {
		return Valido;
	}
}
