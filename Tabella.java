package gol;

public class Tabella {
	
	cella[][] oldGeneration; //quella di riferimento!
	cella[][] newGeneration;
	int dim;
	
	Tabella(int dim){
		this.dim=dim;
		oldGeneration=new cella[dim][dim];
		newGeneration=new cella[dim][dim];
		for(int i=0;i<dim;i++) {
			for(int j=0;j<dim;j++) {
				oldGeneration[i][j]=new cella(j,i);
				newGeneration[i][j]=new cella(j,i);
			}
		}
	}
	
	public void changeState(int i,int j) {
		oldGeneration[i][j].changeAlive();
	}
	
	
	public void evolve() {
		for(int i=0;i<dim;i++) {
			for(int j=0;j<dim;j++) {
				newGeneration[i][j].setAlive(oldGeneration[i][j].nextState());
			}
		}
		
		for(int i=0;i<dim;i++) {
			for(int j=0;j<dim;j++) {
				oldGeneration[i][j].setAlive(newGeneration[i][j].isAlive());
			}
		}
		
		//AL POSTO DI OLD GENERATION=NEW GENERATION... ???? riflettici
	}
	
	public boolean getStateOfOneCell(int x,int y) {
		return oldGeneration[y][x].isAlive();
	}
	
	
	public void drawTabella() {
		
		for(int i=0;i<dim;i++) {
			for(int j=0;j<dim;j++) {
				if(oldGeneration[i][j].isAlive()) {
					System.out.print("|*");
				}else {
					System.out.print("| ");
				}
			}
			System.out.print("\n");
		}
		System.out.print("\n");
		System.out.print("\n");
	}
	
	
	class cella{
		private boolean alive=false;
		private int x;
		private int y;
		
		cella(int x,int y){
			this.x=x;
			this.y=y;
			alive=false;
		}
		
		public boolean isAlive() {
			return alive;
		}
		
		public void changeAlive() {
			alive=(!alive);
		}

		public void setAlive(boolean alive) {
			this.alive = alive;
		}

		public int getX() {
			return x;
		}

		public int getY() {
			return y;
		}
		
		
		public boolean nextState() {
			int i,j,counter=0;
			
			for(i=Math.max(y-1,0);i<Math.min(dim,y+2);i++) {
				for(j=Math.max(x-1,0);j<Math.min(dim,x+2);j++) {
					if(oldGeneration[i][j].isAlive() && !(i== y && j==x)) {
						counter++;
					}
				}
			}
			//System.out.println(y+", "+x+", counter:"+counter);
			
			if(counter<2) {
				return false;
			}else if((counter==2 || counter==3) && alive==true) {
				return true;
			}else if(alive==true && counter>3) {
				return false;
			}else if(alive==false && counter==3) {
				return true;
			}else {
				return false;
			}
		}
		
	}

}
