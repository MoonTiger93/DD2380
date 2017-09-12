
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;


// Xt = xi,i ∈ {1,2,...N} : N possible hidden states Ot=ok,k∈{1,2,...K}:K possibleobservations
// πi = P (X1 = i ) : the initial probability vector π ∈ R1,N with elements πi
// ai,j =P(Xt+1=j|Xt=i):thetransitionprobabilitymatrixA∈RN,N withelementsai,j
// bi (k) = P(Ot = k|Xt = i) : the observation probability matrix B ∈ RN,K with elements bi,k = bi (k)

public class bPass {

    Matriximo A;
    Matriximo B;
    Matriximo PI;
    int[] O;
    int N;
    int T;

    bPass(Matriximo Ain, Matriximo Bin,Matriximo PIin, int[] Oin){
      A = Ain;
      B = Bin;
      PI = PIin;
      O = Oin;
      N=PI.m;
      T = O.length;
    }
    String calcBeta(){

        float delta[][]=new float[T][N]; //the delta that saves the probability of each state at each timeframe
        int deltaIdx[][]=new int[T][N]; //the delta that saves the probability of each last state given a state and a timeframe

        for(int i=0;i<N;i++){ //2.17 first row of probabilities
        	delta[0][i]=B.getValue(i,O[0])*PI.getValue(0,i);
        }



        for(int t=1;t<T;t++){ //2.19-2.20
        	//for each timeframe
        	for(int i=0;i<N;i++){
            //and for each state

        		float maxProb=0; //will be the probability of the state
        		for(int j=0;j<N;j++){ //this is the loop over the last states
              float newProb = A.getValue(j,i)*delta[t-1][j]*B.getValue(i,O[t]);
              if (newProb == maxProb){
                //fixa sen om dubbletter behövs räknas med
              }
              if (newProb > maxProb){
                delta[t][i]=newProb;
                deltaIdx[t][i]=j;
                maxProb = newProb;
              }
        		}

        		//System.out.println("delta deltaIdx[t][i] :	"+ deltaIdx[t][i]);
        	}
        }




        float maxDelta=0;
        int maxDeltaIdx=-2;

        for(int j=0;j<N;j++){
        	if (delta[T-1][j]>maxDelta){
            maxDelta=delta[T-1][j];
        	  maxDeltaIdx=j;

           }
        }
        String returnstring = "";
        int[] xStar=new int[T];

        xStar[T-1]=maxDeltaIdx;

        for(int t=T-2;t>-1;t--){//2.22

        	xStar[t]=deltaIdx[t+1][xStar[t+1]];

        }

       	for(int t=0;t<T;t++){
          returnstring = (returnstring + String.valueOf(xStar[t]) + " ");
       	}
        return returnstring;
      }
}
