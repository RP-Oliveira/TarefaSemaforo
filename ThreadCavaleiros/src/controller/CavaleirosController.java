package controller;

import java.util.concurrent.Semaphore;

public class CavaleirosController extends Thread {
	static int tocha = 1;
	static int pedra = 1 ;
	int percurso = 2000;
	static int [] porta = {1,1,1,1};
	static int indice;
	static int cavaleiro=0;
	private Semaphore semaforo;
	private Semaphore item = new Semaphore(1);
	
	private Semaphore corrida = new Semaphore(1);
	public CavaleirosController(Semaphore semaforo) {
		this.tocha = tocha;
		this.pedra = pedra;
		this.percurso = percurso;
		this.semaforo = semaforo;
		
	}

	public void run() {
			
		try {
			corrida.acquire();
			cavaleiro();
			Thread.sleep(50);
			corrida.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		try {
			semaforo.acquire();
			portaEsperanca();
			semaforo.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	private void cavaleiro() {
		int tochabuff = 0;
		int distancia = 0;
		int velMin = 2;
		int velMax = 4;
		
		while (distancia < percurso) {
		
			if (distancia >= 500 && this.tocha > 0) {
				try {
					item.acquire();
					this.tocha = 0;
					tochabuff++;
					velMin+=2;
					velMax+=2;
					System.out.println("!!! Cavaleiro #"+threadId()+" pegou a tocha !!!");
					item.release();
				} catch (InterruptedException e) {
				}
				
			}
			
			if (distancia >= 1500 && this.pedra > 0) {
				if (tochabuff == 0) {
					try {
						item.acquire();
						this.pedra=0;
						velMin+=2;
						velMax+=2;
						System.out.println("!!!! Cavaleiro #"+threadId()+" pegou a pedra !!!!");
						item.release();
					} catch (InterruptedException e) {
					}	

				}
			}
			
			distancia+=movimento(velMin,velMax);
			int threadId = (int)threadId();
			//System.out.println("Cavaleiro #"+threadId+"moveu-se "+distancia+" metros");
			
		}
		
		
		
	}
	
	private void portaEsperanca() {
		
		indice=(int) (Math.random()*(4-0)+0);
		
		
		while (cavaleiro < 3 && porta[indice] == 0) {
			indice=(int) (Math.random()*(4-0)+0);
			System.out.println("porta "+indice+"tentada");
		}
		if (indice==3 && porta[indice] !=0){
			System.out.println("!!!<<Cavaleiro #"+threadId()+" Venceu>>!!!");
			CavaleirosController.porta[indice]= 0;
			cavaleiro++;
		} else {
			
			if(indice<3 && porta[indice] != 0) {
			System.out.println("!!! Cavaleiro #"+threadId()+" Foi morto por uma besta!!!");
			CavaleirosController.porta[indice]= 0;
			cavaleiro++;
			}
		}
		
		
			

		
	}
	
	private int movimento (int velMin, int velMax) {
		return (int) (Math.random()*((velMax+1)-velMin)+velMin);
	}
}
