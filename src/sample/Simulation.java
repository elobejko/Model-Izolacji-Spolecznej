package sample;

import java.util.Random;

public class Simulation {
	
	/**
	 * Numbers in chain:
	 * 0 - empty
	 * 1 - isolated
	 * 2,3,4... - opinions
	 */
	
	public int[] chain;
	int opinions=0;
	
	Random rand = new Random();
	
	public Simulation(int chainSize, int numberOpinions) {
		chain=new int[chainSize];
		for(int i=0; i<chainSize; i++){
			chain[i]=0;
		}
		opinions=numberOpinions;
		int isolations=0;
		int numberTry=0; //Liczba prÛb znalezienia pustego elementu ≥aÒcucha.
						 //Jeøeli prÛb bÍdzie za duøo, zostanie wybrany pierwszy pusty
		for(int i=0; i<chainSize; i++){
			int n=rand.nextInt(chainSize-1); //Losuj element, ktÛry bÍdzie zape≥niony
			if (chain[n]>=1){ //Jeøeli wylosowany jest juø zajÍty element, sprÛbuj ponownie (max 10 razy)
				if (numberTry>10){
					i--;
					numberTry++;
					continue;
				}
				else{ //Jeøeli nadal nie wylosowano pustego, weü pierwszy z brzega
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
			//Sprawdzenie otoczenia - czy nastÍpuje jakaú izolacja
			//Jeøeli opinia jest otoczona przez dwie inne (ale wzajemnie takie same), nastÍpuje izolacja
			if(chainSize-(n+1)>=2){
				if(chain[n]==chain[n+2]&chain[n+1]!=chain[n]){
					chain[n+1]=1;
					isolations++;
				}
			}
			else if(chainSize-(n+1)<=chainSize-2){
				if(chain[n]==chain[n-2]&chain[n-1]!=chain[n]){
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
			for(int j=0; j<chainSize; j++)
				System.out.print(chain[j]);
			System.out.println();
		}
	}
	public static void main(String[] args) {
        Simulation simulacja = new Simulation(10,2);
    }
}
