package pt.iscte.poo.sokobanstarter;

import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;

public class Empilhadora extends GameElement {

//	private Point2D position;
//	private String imageName;
	private int energy;
	private boolean martelo;

	public Empilhadora(Point2D initialPosition) {
		super(initialPosition, "Empilhadora_D", 3);
		energy = 100;
		martelo = false;
		// imageName = "Empilhadora_D";
		// position = initialPosition;
	}

	/*
	 * @Override public String getName() { return imageName; }
	 * 
	 * @Override public Point2D getPosition() { return position; }
	 * 
	 * @Override public int getLayer() { return 2; }
	 */
	@Override
	public boolean isTransposable() {
		return false;
	}

	public int getEnergy() {
		return energy;
	}

	public void martelo() {
		System.out.println("\033[1;4;36mApanhou o Martelo! \nAgora podes partir as Paredes Rachadas!\033[0m");
		martelo = true;
	}

	public void move(Direction dir) {

		// Gera uma direcao aleatoria para o movimento
//		Direction[] possibleDirections = Direction.values();
//		Random randomizer = new Random();
//		int randomNumber = randomizer.nextInt(possibleDirections.length);
//		Direction randomDirection = possibleDirections[randomNumber];

		// Move segundo a direcao gerada, mas so' se estiver dentro dos limites
		Point2D newPosition = getPosition().plus(dir.asVector());
		if (energy == 0) {
			System.out.println("\033[4;30mAcabou a sua Energia!\033[0m");
		}
		if (energy > 0) {
			if (andavel(newPosition, dir)) {
				if (newPosition.getX() >= 0 && newPosition.getX() < 10 && newPosition.getY() >= 0
						&& newPosition.getY() < 10) {
					setPosition(newPosition);
				}
			}
		}

		if (dir == Direction.DOWN)
			setName("Empilhadora_D");
		if (dir == Direction.LEFT)
			setName("Empilhadora_L");
		if (dir == Direction.RIGHT)
			setName("Empilhadora_R");
		if (dir == Direction.UP)
			setName("Empilhadora_U");

		GameElement g = GameEngine.getInstance().getObjectAtPositionL1(newPosition);
		if (g instanceof Teleporte) {
			((Item) g).consume(newPosition, this);
		}

	}

	public boolean andavel(Point2D p, Direction d) {
		GameElement g = GameEngine.getInstance().getObjectAtPositionL1(p);
		GameElement nexte = GameEngine.getInstance().getObjectAtPositionL1(p.plus(d.asVector()));
		GameElement nextm = GameEngine.getInstance().getMovableAtPosition(p.plus(d.asVector()));
		GameElement m = GameEngine.getInstance().getMovableAtPosition(p);
		if (g == null ) {
			consomeEnergia(1);
			return true;
		}
		if (g.isTransposable()) {
			consomeEnergia(1);
			return true;
		}

		if (martelo == true) {
			if (g instanceof ParedeRachada) {
				GameEngine.getInstance().remove(g);
				consomeEnergia(1);
				return true;
			}

		}
		if (m instanceof Movable && !(m.isTransposable())) {
			if (nexte == null) {
				consomeEnergia(1);
				((Movable) m).push(d);
				consomeEnergia(1);
				if (nexte instanceof Teleporte) {
					((Teleporte) nexte).consume(p.plus(d.asVector()), m);
				}
				
				GameEngine.getInstance().Alvo();
				return true;
			}
			
			
			if (nexte.isTransposable() || ((nexte instanceof Teleporte && !(nextm instanceof Movable))
					|| m instanceof Palete && nexte instanceof Buraco && !(nexte.isTransposable()))) {
				((Movable) m).push(d);
				consomeEnergia(1);
				if (nexte instanceof Teleporte) {
					((Teleporte) nexte).consume(p.plus(d.asVector()), m);
				}
				
				GameEngine.getInstance().Alvo();
				return true;
			} else {
				if (nextm instanceof Palete && (((Palete) nextm).isTransposable())) {
					((Movable) m).push(d);
					consomeEnergia(1);
					return true;
				}
				return false;
			}
		}
		if (g instanceof Item) {
			((Item) g).consume(p, this);
			consomeEnergia(1);
			return true;
		}
		return false;
	}

	public void consomeEnergia(int nrc) {
		energy -= nrc;
		String cor = "\u001B[30m"; //branco
		
		if (energy > 100 ) {
			cor = "\u001B[35m"; //roxo
		}
		if (energy <= 100 ) {
			cor = "\u001B[32m"; //verde
		}
		if (energy < 60 ) {
			cor = "\u001B[33m"; //amarelo
		}
		
		if (energy < 30  ) {
			cor = "\u001B[31m"; //vermelho
		}
		System.out.println(cor + "Resta-lhe " + energy + " de bateria!\033[0m");
		
		if (energy == 0) {
			System.out.println("\033[4;30mAcabou a sua Energia!\033[0m");
		}
		
	}

	public void bateria() {
		System.out.println("\u001B[1;4;33mApanhou a Bateria! +50 bateria!\033[0m");
		energy += 50;
	}

}