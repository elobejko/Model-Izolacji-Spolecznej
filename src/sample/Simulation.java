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
	
	public static int[] chain;
	public static int[] isolationsTable;
	int opinions=0;
	
	Random rand = new Random();
	
	public Simulation(int chainSize, int numberOpinions) {
		chain=new int[chainSize];
		isolationsTable=new int[chainSize];
		for(int i=0; i<chainSize; i++){
			chain[i]=0;
		}
		opinions=numberOpinions;
		int isolations=0;
		int numberTry=0; //Liczba pr�b znalezienia pustego elementu �a�cucha.
						 //Je�eli pr�b b�dzie za du�o, zostanie wybrany pierwszy pusty
		for(int i=0; i<chainSize; i++){
			int n=rand.nextInt(chainSize-1); //Losuj element, kt�ry b�dzie zape�niony
			if (chain[n]>=1){ //Je�eli wylosowany jest ju� zaj�ty element, spr�buj ponownie (max 10 razy)
				if (numberTry>10){
					i--;
					numberTry++;
					continue;
				}
				else{ //Je�eli nadal nie wylosowano pustego, we� pierwszy z brzega
					for (int j=0; j<n; j++){
						if (chain[j]==0){
							n=j;
							numberTry=0;
							break;
						}
					}
				}
			}
			chain[n]=2+rand.nextInt(opinions);
			//Sprawdzenie otoczenia - czy nast�puje jaka� izolacja
			//Je�eli opinia jest otoczona przez dwie inne (ale wzajemnie takie same), nast�puje izolacja
			if(chainSize-(n+1)>=2){
				if(chain[n]==chain[n+2]&chain[n+1]!=chain[n]&chain[n+1]!=0){
					chain[n+1]=1;
					isolations++;
				}
			}
			else if(chainSize-(n+1)<=chainSize-2){
				if(chain[n]==chain[n-2]&chain[n-1]!=chain[n]&chain[n-1]!=0){
					chain[n-1]=1;
					isolations++;
				}
			}
			else if(chainSize-(n+1)<=chainSize-1&chainSize-(n+1)>=1){
				if(chain[n+1]==chain[n-2]&chain[n+1]!=chain[n]){
					chain[n]=1;
					isolations++;
				}
			}
			isolationsTable[i]=isolations;
		}
	}
	
	public File fileChooser(){
		JFileChooser chooser = new JFileChooser(); // Stworzenie klasy
        chooser.setDialogTitle("Zapisywanie wynik�w"); // Ustawienie tytu�u okienka
        int result = chooser.showDialog(null, "Zapisz"); //Otwarcie okienka. Metoda ta blokuje si� do czasu wybrania pliku lub zamkni�cia okna
        if (JFileChooser.APPROVE_OPTION == result){ //Je�li u�ytkownik wybra� plik
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
	
	/*public static void main(String[] args) {
        Simulation symulacja = new Simulation(1000,2);
        symulacja.save();
    }*/
}
