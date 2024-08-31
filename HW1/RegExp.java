import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class RegExp {
    
    public static boolean PalinDrom(String str)
    {
        if(str.length()==0){ //empty string
            return true;
        }

        int i = 0; //head
        int j = str.length() - 1; //tail
        str = str.toLowerCase();
        while(str.charAt(i) == str.charAt(j)){
            i++;
            j--;
            if(i>=j){
                return true;
            }
        }
        return false;
    }
    
    /*
    public static boolean HaveStr1(String str, String str1)
    {
        if(str.length()==0){ //empty string
            return false;
        }

        str = str.toLowerCase();
        str1 = str1.toLowerCase();
        if(str.contains(str1)){
            return true;
        }
        else{
            return false;
        }
    }
    */

    public static boolean HaveStr1(String str, String str1)
    {
        if(str.length()==0){ //empty string
            return false;
        }

        str = str.toLowerCase();
        str1 = str1.toLowerCase();

        for(int i=0; i<str.length()-str1.length()+1 ;i++){
            boolean found = true;
            for(int j=0;j<str1.length();j++){
                if(str.charAt(i+j) != str1.charAt(j)){
                    found = false;
                    break;
                }
            }
            if(found){
                return true;
            }
        }
        return false;
    }

    public static boolean HaveStr2_nTimes(String str, String str2, int n)
    {
        if(str.length()==0){ //empty string
            return false;
        }

        str = str.toLowerCase();
        str2 = str2.toLowerCase();
        int count=0;
        for(int i=0; i<str.length()-str2.length()+1 ;i++){
            for(int j=0;j<str2.length();j++){
                if(str.charAt(i+j) != str2.charAt(j)){
                    break;
                }
                if(j == str2.length() - 1){
                    count++;
                }
            }
        }
        if(count>=n){
            return true;
        }
        else{
            return false;
        }
    }

{
    /*public static boolean Have_aM_b2M(String strings)
    {
        
        strings = strings.toLowerCase();
        int []appear = new int[strings.length()]; //
        
        for(int i=0; i<strings.length() ;i++){
            if(strings.charAt(i)=='a'){
                appear[i] = 1; //'a'
            }
            else if(strings.charAt(i)=='b'){
                appear[i] = 2; //'b'
            }
            else{
                appear[i] = 0; //
            }
        }

        int m = 0; //
        int n = 0; //
        int state; // 0: counting X, 1: counting 'a', 2: counting 'b'


        //
        state = 1;
        for (int i = strings.indexOf('a'); i < appear.length; i++) {
            
            switch (state) {
                case 0:
                    if(i==appear.length-1){
                        break;
                    }
                    if (appear[i+1] == 0) {
                        state = 0;
                    } else if (appear[i+1] == 1) {
                        state = 1;
                        m=0;
                        n=0;
                    } else if (appear[i+1] == 2) {
                        n=0;
                        state = 2;
                    }
                    break;
            
                case 1:
                    m++;
                    if(m >= 1 && n == 2 * m){
                        return true;
                    }
                    if(i==appear.length-1){
                        break;
                    }
                    if (appear[i+1] == 0) {
                        state = 0;
                    } else if (appear[i+1] == 1) {
                        state = 1;
                    } else if (appear[i+1] == 2) {
                        n=0;
                        state = 2;
                    }
                    break;

                case 2:
                    n++;
                    if(m >= 1 && n == 2 * m){
                        return true;
                    }
                    if(i==appear.length-1){
                        break;
                    }
                    if (appear[i+1] == 0) {
                        state = 0;
                        m=0;
                        n=0;
                    } else if (appear[i+1] == 1) {
                        state = 1;
                        m=0;
                        n=0;
                    } else if (appear[i+1] == 2) {
                        state = 2;
                    }
                    break;
            } 
        }
        return false;
    }*/
}

    /*
    public static boolean Have_aM_b2M(String str){

        if(str.length()==0){ //empty string
            return false;
        }

        str = str.toLowerCase();
        if(str.indexOf('a') != -1){ //'a' exist
        
            if((str.indexOf('a') != str.length()-1)&&(str.indexOf('a') != str.length()-2)){ //'a' is not at the last or
                
                for(int i = str.indexOf('a')+1; i<str.length()-1; i++){
                    if( (str.charAt(i) == 'b') && (str.charAt(i+1) == 'b') ){
                        return true;
                    }
                }

                return false;
            }
            else{
                return false;
            }

        }
        else{
            return false; //'a' not exist
        }
    }
    */
    
    public static boolean Have_aM_b2M(String str){

        if(str.length()<3){ //empty string or too short
            return false;
        }

        int a_first_appear = -1;// -1: 'a' not exist
        str = str.toLowerCase();

        for(int i=0; i<str.length(); i++){
            if(str.charAt(i) == 'a'){
                a_first_appear = i;
                break;
            }
        }

        if(a_first_appear != -1){ // 'a' exist
        
            if((a_first_appear != str.length()-1)&&(a_first_appear != str.length()-2)){ //'a' is not at the last or
                
                for(int i = a_first_appear+1; i<str.length()-1; i++){
                    if( (str.charAt(i) == 'b') && (str.charAt(i+1) == 'b') ){
                        return true;
                    }
                }
                return false;
            }
            else{
                return false;
            }
        }
        else{
            return false; // 'a' not exist
        }
    }

    public static void main(String[] args) {
        String str1 = args[1];
        String str2 = args[2];
        int n = Integer.parseInt(args[3]);

        /*
        For your testing of input correctness
        System.out.println("The input file:"+args[0]);
        System.out.println("str1="+str1);
        System.out.println("str2="+str2);
        System.out.println("num of repeated requests of str2 = "+n);
        */

        try {
            BufferedReader reader = new BufferedReader(new FileReader(args[0]));
            String line;
            
            while ((line = reader.readLine()) != null) {
                if(PalinDrom(line)){
                    System.out.print("Y,");
                }
                else{
                    System.out.print("N,");
                }

                if(HaveStr1(line,str1)){
                    System.out.print("Y,");
                }
                else{
                    System.out.print("N,");
                }

                if(HaveStr2_nTimes(line,str2,n)){
                    System.out.print("Y,");
                }
                else{
                    System.out.print("N,");
                }

                if(Have_aM_b2M(line)){
                    System.out.print("Y");
                }
                else{
                    System.out.print("N");
                }

                if(reader.ready()){  // check if there is a next  line
                    System.out.println(); 
                }
                //System.out.println(line);
            }
            
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
