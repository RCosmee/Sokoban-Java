package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Palete extends GameElement implements Movable {
	public Palete(Point2D Point2D) {
		super(Point2D, "Palete", 3);
		setTransposable(false);
	}

	public void push(Direction dir) {
		Point2D newPosition = getPosition().plus(dir.asVector());
		if (newPosition.getX() >= 0 && newPosition.getX() < 10 && newPosition.getY() >= 0 && newPosition.getY() < 10) {
			setPosition(newPosition);
			Fixo();
		}
	}


	public void Fixo() {
		if (GameEngine.getInstance().getBuracoAtPosition(getPosition()) != null && !(GameEngine.getInstance().getBuracoAtPosition(getPosition()).isTransposable())) {
			GameEngine.getInstance().getBuracoAtPosition(getPosition()).setTransposable(true);
			setTransposable(true);
			setLayer(2);
			GameEngine.getInstance().getBobcat().consomeEnergia(1);
		}
	}

	
	
}