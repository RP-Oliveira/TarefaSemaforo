package view;
import java.util.concurrent.Semaphore;

import controller.CavaleirosController;
public class CavaleirosPrincipal {

	public static void main(String[] args) {
		
		int permissao =1;
		Semaphore semaforo = new Semaphore(permissao);

		
		for (int i=0; i<5; i++) {
			CavaleirosController cavaleiro = new CavaleirosController(semaforo);
			cavaleiro.start();
		}

	}

}
