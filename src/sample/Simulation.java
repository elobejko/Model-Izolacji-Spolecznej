package sample;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import javax.swing.JFileChooser;

public class Simulation {
	
	/**
	 * Numbers in chain:
	 * 0 - empty
	 * 1 - isolated
	 * 2,3,4... - opinions
	 */
	
	public static int[][] chain;
	public static int[] isolationsTable;
	int opinions=0;
	
	Random rand = new Random();
	
	public Simulation(int chainSize, int numberOpinions, int numberChains) {
		chain=new int[chainSize][numberChains];
		isolationsTable=new int[chainSize*numberChains];
		for(int i=0; i<chainSize; i++){
			for(int j=0; j<numberChains; j++){
				chain[i][j]=0;
			}
		}
		opinions=numberOpinions;
		int isolations=0;
		int numberTry=0; //Liczba prób znalezienia pustego elementu łańcucha.
						 //Jeżeli prób będzie za dużo, zostanie wybrany pierwszy pusty
		for(int i=0; i<chainSize*numberChains; i++){
			int n=rand.nextInt(chainSize); //Losuj element, który będzie zapełniony
			int k=0;
			if(numberChains>1)
				k=rand.nextInt(numberChains); //Losuj łańcuch
			if (chain[n][k]>0){ //Jeżeli wylosowany jest już zajęty element, spróbuj ponownie (max 10 razy)
				if (numberTry<10){
					i--;
					numberTry++;
					continue;
				}
				else{ //Jeżeli nadal nie wylosowano pustego, weź pierwszy z brzega
					for (int j=0; j<=n; j++){
						for (int l=0; l<=k; l++){
							if (chain[j][l]==0){
								n=j;
								numberTry=0;
								break;
							}
						}
						if(numberTry==0)
							break;
					}
				}
			}
			else
				numberTry=0;
			chain[n][k]=2+rand.nextInt(opinions);
			//Sprawdzenie otoczenia - czy następuje jakaś izolacja
			//Jeżeli opinia jest otoczona przez dwie inne (ale wzajemnie takie same), następuje izolacja
			if(numberChains==1){ //Pojedyńczy łańcuch
				if(chainSize-(n+1)>=2){
					if(chain[n][k]==chain[n+2][k]&chain[n+1][k]!=chain[n][k]&chain[n+1][k]!=0){
						chain[n+1][k]=1;
						isolations++;
					}
				}
				if(n>1){
					if(chain[n][k]==chain[n-2][k]&chain[n-1][k]!=chain[n][k]&chain[n-1][k]!=0){
						chain[n-1][k]=1;
						isolations++;
					}
				}
				if(n>0&chainSize-(n+1)>=1){
					if(chain[n+1][k]!=0&chain[n+1][k]==chain[n-1][k]&chain[n+1][k]!=chain[n][k]){
						chain[n][k]=1;
						isolations++;
					}
				}
			}
			else{ //N - łańcuchów 
				//Przypadek w środku łańcucha
				if(chainSize-(n+1)>=2&numberChains-(k+1)>=1&k>0){
					if(chain[n][k]==chain[n+2][k]&chain[n+1][k]!=chain[n][k]&chain[n+1][k]!=0&chain[n+1][k+1]==chain[n+1][k-1]){
						chain[n+1][k]=1;
						isolations++;
					}
				}
				if(n>1&numberChains-(k+1)>=1&k>0){
					if(chain[n][k]==chain[n-2][k]&chain[n-1][k]!=chain[n][k]&chain[n-1][k]!=0&chain[n-1][k+1]==chain[n-1][k-1]){
						chain[n-1][k]=1;
						isolations++;
					}
				}
				if(n>0&chainSize-(n+1)>=1&numberChains-(k+1)>=1&k>0){
					if(chain[n+1][k]!=0&chain[n+1][k]==chain[n-1][k]&chain[n+1][k]!=chain[n][k]&chain[n][k+1]==chain[n][k-1]){
						chain[n][k]=1;
						isolations++;
					}
				}
				//Przypadek na granicznych łańcuchach
				if(chainSize-(n+1)>=2&k==0){
					if(chain[n][k]==chain[n+2][k]&chain[n+1][k]!=chain[n][k]&chain[n+1][k]!=0&chain[n+1][k+1]==chain[n+2][k]){
						chain[n+1][k]=1;
						isolations++;
					}
				}
				if(chainSize-(n+1)>=2&k==numberChains-1){
					if(chain[n][k]==chain[n+2][k]&chain[n+1][k]!=chain[n][k]&chain[n+1][k]!=0&chain[n+1][k-1]==chain[n+2][k]){
						chain[n+1][k]=1;
						isolations++;
					}
				}
				if(n>1&k==0){
					if(chain[n][k]==chain[n-2][k]&chain[n-1][k]!=chain[n][k]&chain[n-1][k]!=0&chain[n-1][k+1]==chain[n-2][k]){
						chain[n-1][k]=1;
						isolations++;
					}
				}
				if(n>1&k==numberChains-1){
					if(chain[n][k]==chain[n-2][k]&chain[n-1][k]!=chain[n][k]&chain[n-1][k]!=0&chain[n-1][k-1]==chain[n-2][k]){
						chain[n-1][k]=1;
						isolations++;
					}
				}
				if(n>0&chainSize-(n+1)>=2&k==0){
					if(chain[n+1][k]!=0&chain[n+1][k]==chain[n-1][k]&chain[n+1][k]!=chain[n][k]&chain[n][k+1]==chain[n+1][k]){
						chain[n][k]=1;
						isolations++;
					}
				}
				if(n>0&chainSize-(n+1)>=2&k==numberChains-1){
					if(chain[n+1][k]!=0&chain[n+1][k]==chain[n-1][k]&chain[n+1][k]!=chain[n][k]&chain[n][k-1]==chain[n+1][k]){
						chain[n][k]=1;
						isolations++;
					}
				}
				//TODO Dodać opcje gdy izolacja będzie w elemencie nad/pod bierzącym
				//TODO Pozamieniać else if na if (bo może więcej niż jedna sytuacja zajść w tej samej iteracji)
				//TODO Jeszcze raz przejrzeć wszystkie ifowe warunki, jest błąd w >= i <=
			}
			isolationsTable[i]=isolations;
			//Tylko do podgladu dla malych lancuchow!
			/**for(int j=0; j<numberChains; j++){
				for(int l=0; l<chainSize; l++){
					System.out.print(chain[l][j]);
				}
				System.out.println();
			}
			System.out.println(i);**/
		}
	}
	
	public File fileChooser(){
		JFileChooser chooser = new JFileChooser(); // Stworzenie klasy
        chooser.setDialogTitle("Zapisywanie wyników"); // Ustawienie tytułu okienka
        int result = chooser.showDialog(null, "Zapisz"); //Otwarcie okienka. Metoda ta blokuje się do czasu wybrania pliku lub zamknięcia okna
        if (JFileChooser.APPROVE_OPTION == result){ //Jeśli użytkownik wybrał plik
        	return chooser.getSelectedFile();
        }
        else {
            System.out.println("Nie wybrano pliku");
            return null;
        }
	}	
	public void save(){
		try {
			File plik = fileChooser();
			PrintWriter out = new PrintWriter(plik);
			out.println("Iteracja \t Izolacje");
			for(int i=0; i<isolationsTable.length;i++){
				out.print(i);
				out.print("\t");
				out.println(isolationsTable[i]);
			}
		    out.close();
		} 
			catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
        Simulation symulacja = new Simulation(100000,2,10);
        symulacja.save();
    }
}
