import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.List;

public class CodeGenerator {

    public static String toString(char[] a)
    {
        // Creating object of String class
        String string = new String(a);
 
        return string;
    }
    
    public static ClsInfo[] mermaidCodeFileReader(String arr){

        //ArrayList<ClsInfo> clsInfos_ArrayList = new ArrayList<ClsInfo>();
        ClsInfo[] clsInfoArr = new ClsInfo[10];
        for (int i = 0; i < clsInfoArr.length; i++){
            clsInfoArr[i] = new ClsInfo();
        }

        int idxofclsinfoarr_tmp=0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(arr));
            String line;
            
            int idxofClsInfoArr = -1;

            while ((line = reader.readLine()) != null) {
                
                if(line.equals("")){
                    continue;
                }
                line = line.trim(); //remove all the blank at the begin and the end
                
                if(line.equals("classDiagram")){
                    continue;
                }
                else if(line.equals("}")){ //only "}"
                    continue;
                }
                else{
                    if(line.startsWith("class")){ //defining class
                        
                        idxofClsInfoArr++;
                        Parser parser1 = new Parser();
                        String classname = parser1.classNameKnower(line);

                        int idxofclsinfo_tmp;
                        for(idxofclsinfo_tmp=0; idxofclsinfo_tmp<=idxofClsInfoArr; idxofclsinfo_tmp++){
                            if(clsInfoArr[idxofclsinfo_tmp].classNamestr.equals(classname)){
                                break;
                            }
                        }
                        clsInfoArr[idxofclsinfo_tmp].classNamestr = classname;

                        idxofclsinfoarr_tmp = idxofclsinfo_tmp;
                              
                    }
                    else{ //defining member
                        
                        if(line.contains(":")){

                            String clsname_tmp = line.substring(0, line.indexOf(":")).trim();

                            for(int idxofcls_tmp = 0; idxofcls_tmp<clsInfoArr.length; idxofcls_tmp++){
                                
                                if(clsname_tmp.equals(clsInfoArr[idxofcls_tmp].classNamestr)){
                                    
                                    clsInfoArr[idxofcls_tmp].memberCount++;
                                    
                                    if(line.contains("-")){ //Private
                                        clsInfoArr[idxofcls_tmp].isPublicArrLst.add(false);
                                    }
                                    else if (line.contains("+")){ //Public
                                        clsInfoArr[idxofcls_tmp].isPublicArrLst.add(true);
                                    }
            
                                    if(line.contains("(") && line.contains(")")){ //methods
            
                                        if(line.contains("-")){
                                            clsInfoArr[idxofcls_tmp].memberNameArrLst.add(line.substring(line.indexOf("-")+1, line.indexOf(")")+1).trim()); //store member name included parameters
                                        }
                                        else if(line.contains("+")){
                                            clsInfoArr[idxofcls_tmp].memberNameArrLst.add(line.substring(line.indexOf("+")+1, line.indexOf(")")+1).trim()); //store member name included parameters
                                        }                                       
                                        
                                        String cur_memberName = clsInfoArr[idxofcls_tmp].memberNameArrLst.get(clsInfoArr[idxofcls_tmp].memberNameArrLst.size()-1);
                                        
                                        if(cur_memberName.startsWith("set")){ //setter
                                            
                                            clsInfoArr[idxofcls_tmp].memberFuncTypeArrLst.add(1); //setter
                                            
                                            String membername_2 = cur_memberName.substring(3, cur_memberName.indexOf("("));//store the name after "set"
                                            
                                            if(Character.isUpperCase(membername_2.charAt(0))){
                                                char[] membername_2Arr = membername_2.toCharArray();
                                                membername_2Arr[0] = Character.toLowerCase(membername_2Arr[0]);
                                                membername_2 = toString(membername_2Arr);
                                            }
                                            
                                            clsInfoArr[idxofcls_tmp].memberName2ArrLst.add(membername_2);

                                        }
                                        else if(cur_memberName.startsWith("get")){ //getter
                                            
                                            clsInfoArr[idxofcls_tmp].memberFuncTypeArrLst.add(2); //getter
                                            
                                            String membername_2 = cur_memberName.substring(3, cur_memberName.indexOf("("));//store the name after "get"
                                            
                                            if(Character.isUpperCase(membername_2.charAt(0))){
                                                char[] membername_2Arr = membername_2.toCharArray();
                                                membername_2Arr[0] = Character.toLowerCase(membername_2Arr[0]);
                                                membername_2 = toString(membername_2Arr);
                                            }
                                            
                                            clsInfoArr[idxofcls_tmp].memberName2ArrLst.add(membername_2);
                                        }
                                        else{
                                            clsInfoArr[idxofcls_tmp].memberFuncTypeArrLst.add(3); //others
                                            clsInfoArr[idxofcls_tmp].memberName2ArrLst.add("none");
                                        }
                                        
                                        //store member type
                                        clsInfoArr[idxofcls_tmp].memberTypeArrLst.add((line.substring(line.indexOf(")")+1)).trim());
                                    }
                                    else{ //atributes
                                        clsInfoArr[idxofcls_tmp].memberNameArrLst.add(line.substring(line.lastIndexOf(" ")+1)); //store member name
                                        clsInfoArr[idxofcls_tmp].memberName2ArrLst.add("none");
                                        
                                        clsInfoArr[idxofcls_tmp].memberFuncTypeArrLst.add(0); //atributes
            
                                        //store member type
                                        if(line.contains("-")){
                                            clsInfoArr[idxofcls_tmp].memberTypeArrLst.add(line.substring(line.indexOf("-")+1, line.lastIndexOf(" ")+1).trim());
                                        }
                                        else if(line.contains("+")){
                                            clsInfoArr[idxofcls_tmp].memberTypeArrLst.add(line.substring(line.indexOf("+")+1, line.lastIndexOf(" ")+1).trim());
                                        }                                        
                                    }   
                                }
                            }
                        }
                        else{ //inside { }
                            
                            clsInfoArr[idxofclsinfoarr_tmp].memberCount++;

                            if(line.startsWith("-")){ //Private
                                clsInfoArr[idxofclsinfoarr_tmp].isPublicArrLst.add(false);
                            }
                            else if (line.startsWith("+")){ //Public
                                clsInfoArr[idxofclsinfoarr_tmp].isPublicArrLst.add(true);
                            }

                            if(line.contains("(") && line.contains(")")){ //methods

                                clsInfoArr[idxofclsinfoarr_tmp].memberNameArrLst.add(line.substring(1, line.indexOf(")")+1)); //store member name included parameters                           
                                
                                String cur_memberName = clsInfoArr[idxofclsinfoarr_tmp].memberNameArrLst.get(clsInfoArr[idxofclsinfoarr_tmp].memberNameArrLst.size()-1);
                                
                                if(cur_memberName.startsWith("set")){ //setter
                                    clsInfoArr[idxofclsinfoarr_tmp].memberFuncTypeArrLst.add(1); //setter
                                            
                                    String membername_2 = cur_memberName.substring(3, cur_memberName.indexOf("("));//store the name after "set"
                                    
                                    if(Character.isUpperCase(membername_2.charAt(0))){
                                        char[] membername_2Arr = membername_2.toCharArray();
                                        membername_2Arr[0] = Character.toLowerCase(membername_2Arr[0]);
                                        membername_2 = toString(membername_2Arr);
                                    }
                                    
                                    clsInfoArr[idxofclsinfoarr_tmp].memberName2ArrLst.add(membername_2);
                                }
                                else if(cur_memberName.startsWith("get")){ //getter
                                    clsInfoArr[idxofclsinfoarr_tmp].memberFuncTypeArrLst.add(2); //getter
                                            
                                    String membername_2 = cur_memberName.substring(3, cur_memberName.indexOf("("));//store the name after "get"
                                    
                                    if(Character.isUpperCase(membername_2.charAt(0))){
                                        char[] membername_2Arr = membername_2.toCharArray();
                                        membername_2Arr[0] = Character.toLowerCase(membername_2Arr[0]);
                                        membername_2 = toString(membername_2Arr);
                                    }
                                    
                                    clsInfoArr[idxofclsinfoarr_tmp].memberName2ArrLst.add(membername_2);
                                }
                                else{//other methods
                                    clsInfoArr[idxofclsinfoarr_tmp].memberFuncTypeArrLst.add(3); //other methods
                                    clsInfoArr[idxofclsinfoarr_tmp].memberName2ArrLst.add("none");
                                }
                                
                                //store member type
                                clsInfoArr[idxofclsinfoarr_tmp].memberTypeArrLst.add((line.substring(line.indexOf(")")+1)).trim());
                            }
                            else{ //atributes
                                clsInfoArr[idxofclsinfoarr_tmp].memberNameArrLst.add(line.substring(line.lastIndexOf(" ")+1)); //store member name
                                clsInfoArr[idxofclsinfoarr_tmp].memberName2ArrLst.add("none");

                                clsInfoArr[idxofclsinfoarr_tmp].memberFuncTypeArrLst.add(0); //atributes

                                clsInfoArr[idxofclsinfoarr_tmp].memberTypeArrLst.add(line.substring(1, line.indexOf(" "))); //store member type
                            }
                        }  
                    }
                }
            }
            
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return clsInfoArr;
    }

    public static void JavaCodeGenerator(ClsInfo[] clsInfoarr){
        
        for(int idxofclsarr=0; idxofclsarr<clsInfoarr.length; idxofclsarr++){
            
            String code = "";
            code += "public class " + clsInfoarr[idxofclsarr].classNamestr + " {\n";
            
            //int idxofmembername2arrlst = 0;
            
            for(int idxofmember=0; idxofmember<clsInfoarr[idxofclsarr].memberCount; idxofmember++){
                
                if(clsInfoarr[idxofclsarr].isPublicArrLst.get(idxofmember)){
                    code += "    public ";
                }
                else{
                    code += "    private ";
                }

                switch (clsInfoarr[idxofclsarr].memberFuncTypeArrLst.get(idxofmember)){
                    case 0://atributes
                        code += clsInfoarr[idxofclsarr].memberTypeArrLst.get(idxofmember) + " " + clsInfoarr[idxofclsarr].memberNameArrLst.get(idxofmember) + ";\n";
                        break;

                    case 1://setter
                        code += "void " + clsInfoarr[idxofclsarr].memberNameArrLst.get(idxofmember) + " {\n        this." + clsInfoarr[idxofclsarr].memberName2ArrLst.get(idxofmember) 
                              + " = " + clsInfoarr[idxofclsarr].memberName2ArrLst.get(idxofmember) + ";\n    }\n";
                        idxofmember++;
                        break;
                
                    case 2://getter
                        code += clsInfoarr[idxofclsarr].memberTypeArrLst.get(idxofmember) + " " + clsInfoarr[idxofclsarr].memberNameArrLst.get(idxofmember) + " {\n        return "
                        + clsInfoarr[idxofclsarr].memberName2ArrLst.get(idxofmember) + ";\n    }\n";
                        idxofmember++;
                        break;

                    case 3://other methods
                        code += clsInfoarr[idxofclsarr].memberTypeArrLst.get(idxofmember) + " " + clsInfoarr[idxofclsarr].memberNameArrLst.get(idxofmember);
                        if(clsInfoarr[idxofclsarr].memberTypeArrLst.get(idxofmember).equals("int")){
                            code += " {return 0;}\n";
                        }
                        else if(clsInfoarr[idxofclsarr].memberTypeArrLst.get(idxofmember).equals("String")){
                            code += " {return \"\";}\n";
                        }
                        else if(clsInfoarr[idxofclsarr].memberTypeArrLst.get(idxofmember).equals("boolean")){
                            code += " {return false;}\n";
                        }
                        else if(clsInfoarr[idxofclsarr].memberTypeArrLst.get(idxofmember).equals("void")){
                            code += " {;}\n";
                        }
                        break;
                }
            }
            
            code += "}";

            try {
                String output = clsInfoarr[idxofclsarr].classNamestr + ".java";
                File file = new File(output);
                if (!file.exists()) {
                    file.createNewFile();
                }
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    bw.write(code);
                }
                //System.out.println(output);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }

    public static void main (String[] args){
        JavaCodeGenerator(mermaidCodeFileReader(args[0]));
    }


}

class ClsInfo{
    public String classNamestr;
    public int memberCount=0;

    //public boolean[] isPublicArr;
    ArrayList<Boolean> isPublicArrLst = new ArrayList<Boolean>();
    
    //public String[] memberType;
    ArrayList<String> memberTypeArrLst = new ArrayList<String>();
    
    //public String[] memberName;
    ArrayList<String> memberNameArrLst = new ArrayList<String>();
    
    //public int[] memberFuncType; 
    ArrayList<Integer> memberFuncTypeArrLst = new ArrayList<Integer>(); // 0:atribute   1:setter    2:getter    3:other methods
    
    //public String[] memberName2;
    ArrayList<String> memberName2ArrLst = new ArrayList<String>();
}

class Parser{

    public String classNameKnower(String str){
        if(str.contains("{")){
            str = str.substring(5, str.indexOf("{")).trim();
        }
        else{
            str = str.substring(5).trim();
        }
        return str;
    }

}