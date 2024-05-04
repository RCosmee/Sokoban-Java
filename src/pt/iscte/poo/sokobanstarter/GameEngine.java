package pt.iscte.poo.sokobanstarter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import pt.iscte.poo.gui.ImageMatrixGUI;
import pt.iscte.poo.observer.Observed;
import pt.iscte.poo.observer.Observer;
import pt.iscte.poo.utils.Direction;
import pt.iscte.poo.utils.Point2D;




// Note que esta classe e' um exemplo - nao pretende ser o inicio do projeto, 
// embora tambem possa ser usada para isso.
//
// No seu projeto e' suposto haver metodos diferentes.
// 
// As coisas que comuns com o projeto, e que se pretendem ilustrar aqui, sao:
// - GameEngine implementa Observer - para  ter o metodo update(...)  
// - Configurar a janela do interface grafico (GUI):
//        + definir as dimensoes
//        + registar o objeto GameEngine ativo como observador da GUI
//        + lancar a GUI
// - O metodo update(...) e' invocado automaticamente sempre que se carrega numa tecla
//
// Tudo o mais podera' ser diferente!

public class GameEngine implements Observer {

	// Dimensoes da grelha de jogo
	public static final int GRID_HEIGHT = 10;
	public static final int GRID_WIDTH = 10;
	private String pfname = "Points.txt";
	private File file = new File(pfname);
	private int totalPoints = 0;
	private static GameEngine INSTANCE; // Referencia para o unico objeto GameEngine (singleton)
	private ImageMatrixGUI gui; // Referencia para ImageMatrixGUI (janela de interface com o utilizador)
	private List<GameElement> ListE; // Lista de imagens
	private Empilhadora bobcat; // Referencia para a empilhadora
	private List<String> highscores;
	
	private int level=0;
	private String hfname = "Highscores.txt";
    private File hfile = new File(hfname);
    private String username;
	// Construtor - neste exemplo apenas inicializa uma lista de ImageTiles
	private GameEngine() {
		ListE = new ArrayList<>();
	}

	// Implementacao do singleton para o GameEngine
	public static GameEngine getInstance() {
		if (INSTANCE == null)
			return INSTANCE = new GameEngine();
		return INSTANCE;
	}

	// Inicio
	public void start() {

		// Setup inicial da janela que faz a interface com o utilizador
		// algumas coisas poderiam ser feitas no main, mas estes passos tem sempre que
		// ser feitos!

		gui = ImageMatrixGUI.getInstance(); // 1. obter instancia ativa de ImageMatrixGUI
		
		name();
		System.out.println("\033[4;32m\nBoa sorte " + username + "!\033[0m\n");
		gui.setSize(GRID_HEIGHT, GRID_WIDTH); // 2. configurar as dimensoes
		gui.registerObserver(this); // 3. registar o objeto ativo GameEngine como observador da GUI
		gui.go(); // 4. lancar a GUI

		// Criar o cenario de jogo
		//createWarehouse(); // criar o armazem
		// createMoreStuff(); // criar mais algun objetos (empilhadora, caixotes,...)
		readLevel("level"+level);
		gui.update();
		//sendImagesToGUI(); // enviar as imagens para a GUI

		// Escrever uma mensagem na StatusBar
		gui.setStatusMessage("Sokoban Starter - demo");
	}

	public Empilhadora getBobcat() {
		return bobcat;
	}

	// O metodo update() e' invocado automaticamente sempre que o utilizador carrega
	// numa tecla
	// no argumento do metodo e' passada uma referencia para o objeto observado
	// (neste caso a GUI)
	@Override
	public void update(Observed source) {

		int key = gui.keyPressed(); // obtem o codigo da tecla pressionada
		
		if (key == 82) { //R
			System.out.println("\u001B[4;31mReiniciou o Nível " + level + "\033[0m");
			restartLevel();
		}
		if(key<=40 && key >= 37)
			bobcat.move(Direction.directionFor(key));

		gui.update(); // redesenha a lista de ImageTiles na GUI,
						// tendo em conta as novas posicoes dos objetos
	}

	public void name() {
        Scanner s = new Scanner(System.in);
        System.out.print("Qual o seu username?\n");
        username = s.nextLine();
        s.close();
    }
	
	public void end() {
		System.out.println("\033[4;35m-Terminou com " + totalPoints + " Pontos!-\033[0m\n\n\033[4;38mScores de cada nível:\033[0m " );
		Scanner s;
		try {
			s = new Scanner(file);
			while(s.hasNextLine()) {
			System.out.println(s.nextLine());
			}

			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("erro");
		}

		System.out.println("\n\033[4;38mTop 3 highscores:\033[0m " );

		try {
			s = new Scanner(hfile);
			while(s.hasNextLine()) {
			System.out.println(s.nextLine());
			}
			s.close();
		} catch (FileNotFoundException e) {
			System.out.println("erro");
		}
		gui.dispose();
	}
	
	// Criacao da planta do armazem - so' chao neste exemplo
	private void createWarehouse() {

		for (int y = 0; y < GRID_HEIGHT; y++)
			for (int x = 0; x < GRID_HEIGHT; x++)
				add(new Chao(new Point2D(x, y)));
	}

//	// Criacao de mais objetos - neste exemplo e' uma empilhadora e dois caixotes
//	private void createMoreStuff() {
//		bobcat = new Empilhadora( new Point2D(5,5));
//		tileList.add(bobcat);
//
//		tileList.add(new Caixote(new Point2D(3,3)));
//		tileList.add(new Caixote(new Point2D(3,2)));
//	}

	
	
	public void readLevel(String nome) {
		createWarehouse(); 
		int y = 0;
        Scanner s;
        try {

			s = new Scanner(new File("levels/" + nome + ".txt"));

            while (s.hasNextLine() && y < GRID_HEIGHT) {
                String line = s.nextLine();
                for (int x = 0; x < GRID_WIDTH; x++) {
                    char character = line.charAt(x);
                    Point2D p= new Point2D(x,y);
                    //identify(character,p);
                    GameElement e = GameElement.createElement(character,p);
                    if(e.getName()=="Empilhadora_D") {
                        bobcat = (Empilhadora) e;
                        }
                    add(e);
                }
                y++;
            }
            s.close();
        } catch (FileNotFoundException e) {
            System.out.println("");
        }
    }
	
	
	
	/*private void identify(char c, Point2D p) {
		if (c == 'E') {
			bobcat = new Empilhadora(p);
			add(bobcat);// (new Empilhadora(new Point2D(x,y)));
			add(new Chao(p));
		}
		if (c == 'C'){
			add(new Caixote(p));add(new Chao(p));}
		if (c == 'X'){
			add(new Alvo(p));add(new Chao(p));}
		if (c == 'B'){
			add(new Bateria(p));add(new Chao(p));}
		if (c == '#')
			add(new Parede(p));
		if (c == ' ')
			add(new Chao(p));
		if (c == '=')
			add(new Vazio(p));
		if (c == 'O'){
			add(new Buraco(p));add(new Chao(p));}
		if (c == 'P'){
			add(new Palete(p));add(new Chao(p));}
		if (c == 'M'){
			add(new Martelo(p));add(new Chao(p));}
		if (c == '%'){
			add(new ParedeRachada(p));add(new Chao(p));}
		if (c == 'T'){
			add(new Teleporte(p));
			add(new Chao(p));
			}
	}*/
	
	public void add(GameElement ge){
		ListE.add(ge);
		gui.addImage(ge);
	}
	
	public void remove(GameElement ge){
		ListE.remove(ge);
		gui.removeImage(ge);
	}
	
	public GameElement getObjectAtPositionL1(Point2D position) {
        for (GameElement element : ListE) {
            if (element.getPosition().equals(position) && !(element.isTransposable())) {
            	//System.out.println(element.getName());
                return element;
            }
        }
        return null;
    }
	public GameElement getMovableAtPosition(Point2D position) {
        for (GameElement element : ListE) {
            if (element.getPosition().equals(position) && (element instanceof Movable)) {
            	if(element.getLayer()==3)
            		return element;
            }
        }
        return null;
    }
	
	public GameElement getBuracoAtPosition(Point2D position) {
        for (GameElement element : ListE) {
            if (element.getPosition().equals(position) && (element instanceof Buraco)) {
                return element;
            }
        }
        return null;
    }
	
	public GameElement getAlvoAtPosition(Point2D position) {
        for (GameElement element : ListE) {
            if (element.getPosition().equals(position) && (element instanceof Alvo)) {
                return element;
            }
        }
        return null;
    }
	
	
	public boolean Alvo() {
        for (GameElement element : ListE) {
        	if(element instanceof Alvo) {
        		if (!((Alvo)element).getValido()) {
        			return false;
        		}
            }
        }
        //System.out.println("acabado");
        nextLevel();
        return true;
    }
	
	public void nextLevel() {
		System.out.println("\033[1;32m\n//////////////////////////////////////////////////");
		System.out.println("\t\tNível concluido!");
		System.out.println("//////////////////////////////////////////////////\033[0m\n");
		addPoints();
		level+=1;
		ListE= new ArrayList<>();
		gui.clearImages();
		readLevel("level"+level);
		if (level > 6) {
			writeHighscore();
			end();
		}	
	}
	
	public void restartLevel() {
		ListE= new ArrayList<>();
		gui.clearImages();
		readLevel("level"+level);
	}
	
//	public void addPoints() {
//		try (PrintWriter out = new PrintWriter(pfname + ".txt")) {			
//			out.print("Level" + level + ": " + bobcat.getEnergy() + " Points!");
//			out.close();
//		} catch (FileNotFoundException e) {
//			System.out.println("erro");
//		}
//	}
	
	    public void addPoints() {
	        totalPoints += bobcat.getEnergy();
	        String content = "Level " + level + ": " + bobcat.getEnergy() + " Points!";

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, level!=0))) {
	            writer.append(content);
	            writer.newLine(); // Add a new line if needed
	        } catch (IOException e) {
	            System.out.println("An error occurred while appending the string to the file.");
	            e.printStackTrace();
	        }
	    }
	    
	    public void sortScores() {
	        highscores.sort(new Comparator<String>() {
	            @Override
	            public int compare(String s1, String s2) {
	                String[] ss1 = s1.split(": ");
	                int score1 = Integer.parseInt(ss1[1]);
	                String[] ss2 = s2.split(": ");
	                int score2 = Integer.parseInt(ss2[1]);
	                return score2 - score1;
	            }
	        });
	    }

	    public void writeHighscore() {

	        highscores = new ArrayList<>();
	        highscores.add(username + " -> total points: " + totalPoints);

	        Scanner s;
	        try {

	            s = new Scanner(hfile);

	            while (s.hasNextLine()) {
	                highscores.add(s.nextLine());

	            }
	            s.close();
	        } catch (FileNotFoundException e) {
	            System.out.println("erro");

	        }

	        sortScores();

	        try (BufferedWriter writer = new BufferedWriter(new FileWriter(hfile))) {
	            for (int i = 0; i < Math.min(3, highscores.size()); i++) {
	                String content = highscores.get(i);
	                writer.append(content);
	                writer.newLine(); // Add a new line if needed
	            }
	        } catch (IOException e) {
	            System.out.println("An error occurred while writing to the file.");
	            e.printStackTrace();
	        }

	    }
	
	    
		public GameElement getObjectTeleport(Point2D position) {
	        for (GameElement element : ListE) {
	            if (!(element.getPosition().equals(position)) && element instanceof Teleporte) {
	            	//System.out.println(element.getPosition().equals(position));
	                return element;     
	            }
	        }
	        return null;
	    }
		
		
	
	/*public GameElement getObjectAtPosition(Point2D position) {
        for (GameElement element : ListE) {
            if (element.getPosition().equals(position)&& !(element instanceof Chao)){
            	System.out.println(element.getName());
                return element;
            }
        }
        return null;
    }*/



	// Envio das mensagens para a GUI - note que isto so' precisa de ser feito no
	// inicio
	// Nao e' suposto re-enviar os objetos se a unica coisa que muda sao as posicoes
	/*private void sendImagesToGUI() {
		gui.addImages(tileList);
	}*/
}
