package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Point2D;
import pt.iscte.poo.gui.ImageTile;

public abstract class GameElement implements ImageTile{

	private Point2D Point2D;
	private String name;
	private int layer;
	private boolean transposable = true;
	
	public GameElement(Point2D Point2D,String name,int layer){
		this.Point2D=Point2D;
		this.name=name;
		this.layer=layer;
	}

	public Point2D getPosition() {
		return Point2D;
	}

	
	public void setPosition(Point2D point2d) {
		Point2D = point2d;
	}

	public String getName() {
		return name;
	}

	public int getLayer() {
		return layer;
	}
	
	public void setLayer(int l) {
		layer=l;
	}
	
	public boolean isTransposable() {
		return transposable;
	}
	public void setTransposable(boolean a){ 
		transposable = a;
	}
	
/*	public void setPosition(Point2D p){
		Point2D=p;
	}*/
	
	public void setName(String name) {
		this.name = name;
	}

	public static GameElement createElement(char c, Point2D p) {
		if (c == 'E') 
			return new Empilhadora(p);
		if (c == 'C')
			return new Caixote(p);
		if (c == 'X')
			return new Alvo(p);
		if (c == 'B')
			return new Bateria(p);
		if (c == '#')
			return new Parede(p);
		if (c == '=')
			return new Vazio(p);
		if (c == 'O')
			return new Buraco(p);
		if (c == 'P')
			return new Palete(p);
		if (c == 'M')
			return new Martelo(p);
		if (c == '%')
			return new ParedeRachada(p);
		if (c == 'T')
			return new Teleporte(p);
		return new Chao(p);
	}

}

