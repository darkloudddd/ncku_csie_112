import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.List;

public class CodeGenerator {
    
    public static String knowTheClass(String str){ //get the class name " BankAccount\nBankAccount : -String owner\n
        
        String [] classNameArr = str.split("\\s+"); // " BankAccount\nBankAccount : -String owner\n ==> {"", "BankAccount", "BankAccount", ":", "-String", "owner"}
        String className = classNameArr[1];
        return className;
    }


    public static void main(String[] args) {
		    // 讀取文件
        if (args.length == 0) {
            //System.err.println("請輸入mermaid檔案名稱");
            return;
        }
        String fileName = args[0];
        //System.out.println("File name: " + fileName);
        
        FileReader mermaidCodeReader = new FileReader();
        String mermaidCode = "";
        mermaidCode = mermaidCodeReader.read(fileName);
        
        Parser mermaidCodeParser = new Parser();
        String[] mermaidCodeArr_splitByClass = mermaidCodeParser.splitByClass(mermaidCode);
        //original mermaidcode was split by "class" into mermaidCodeArr_splitByClass
        
        // 寫入文件
        for(int i=2; i<mermaidCodeArr_splitByClass.length; i++){ //run through all class

            String content = "";
            String className = knowTheClass(mermaidCodeArr_splitByClass[i]); //get the class's name
            content += "public class " + className + " {";
    
            String[] codeArr_splitByClassName = mermaidCodeArr_splitByClass[i].split(className); //get the thing after class's name
            String[] codeArr_splitByBlank; //every line split by blank
                        
            if(codeArr_splitByClassName.length>2){

                for(int k=2; k<codeArr_splitByClassName.length; k++){ //read all things after the class

                    //atributes
                    // " : -String owner\n"
                    String type = "";
                    String atributesName = "";
            
                    //methods
                    //" : +setOwner(String owner) void\n"
                    String methodsName = "";
                    String parameters = "";
                    String returnType = "";

                    codeArr_splitByBlank = codeArr_splitByClassName[k].split("\\s+");
                    
                    if(codeArr_splitByClassName[k].contains("-")){ //private
                                
                        if(codeArr_splitByClassName[k].contains("(") && codeArr_splitByClassName[k].contains(")")){ //has () ==> METHODS !!
                                    
                            methodsName = codeArr_splitByBlank[2].substring(1, codeArr_splitByBlank[2].indexOf('(')); //starts from the word after -/+ to the word before '('                   
                            
                            if(methodsName.startsWith("set")){ //is SETTER !!
                                // " : +setOwner(String owner) void\n" ==> {"", ":", "+setOwner(String", "owner)", "void"}
                                //                                          0    1    2                   3         4
                                parameters = codeArr_splitByBlank[2].substring(codeArr_splitByBlank[2].indexOf('(')) + " " + codeArr_splitByBlank[3];
                                returnType = codeArr_splitByBlank[4];

                                String methodsName2;
                                methodsName2 = methodsName.substring(3); //get the name after "set"
                                methodsName2 = methodsName2.toLowerCase(); //deal the CamelCase

                                content += "\n    private " + returnType + " " + methodsName + parameters + " {\n        this." + methodsName2 + " = " + methodsName2 + ";\n    }";
                            }
                            else if(methodsName.startsWith("get")){ //is GETTER !!
                                // " : +getOwner() String\n" ==> {"", ":", "+getOwner()", "String"}
                                //                                0    1    2             3
                                parameters = "()";
                                returnType = codeArr_splitByBlank[3];

                                String methodsName2;
                                methodsName2 = methodsName.substring(3); //get the name after "get"
                                methodsName2 = methodsName2.toLowerCase(); //deal the CamelCase

                                content += "\n    private " + returnType + " " + methodsName + parameters + " {\n        return " + methodsName2 + ";\n    }";
                            }
                            else{ //other methods
                                // " : +isEnough(int value, int balance) void\n" ==> {"", ":", "+isEnough(int", "value,", "int", "balance)", "void"}
                                //                                                    0    1     2                3         4      5           6          
                                parameters = parameters + codeArr_splitByBlank[2].substring(codeArr_splitByBlank[2].indexOf('('));
                                for(int j=3; j<codeArr_splitByBlank.length-1; j++){
                                    
                                    parameters += " ";
                                    parameters += codeArr_splitByBlank[j];

                                }
                                returnType = codeArr_splitByBlank[codeArr_splitByBlank.length-1];
                                
                                if(returnType.equals("int")){
                                    content += "\n    private " + returnType + " " + methodsName + parameters + " {return 0;}";
                                }
                                else if(returnType.equals("String")){
                                    content += "\n    private " + returnType + " " + methodsName + parameters + " {return \"\";}";
                                }
                                else if(returnType.equals("boolean")){
                                    content += "\n    private " + returnType + " " + methodsName + parameters + " {return false;}";
                                }
                                else if(returnType.equals("void")){
                                    content += "\n    private " + returnType + " " + methodsName + parameters + " {;}";
                                }
                            }
                        }
                        else{ //atributes
                            // " : -int age\n" ==> {"", ":", "-int", "age"}
                            //                      0    1     2       3
                            type = codeArr_splitByBlank[2].substring(1);
                            atributesName = codeArr_splitByBlank[3];
                            content +=  "\n    private " + type + " " + atributesName + ";";
                        }
                                
                    }
                    else if (codeArr_splitByClassName[k].contains("+")){ //public

                        if(codeArr_splitByClassName[k].contains("(") && codeArr_splitByClassName[k].contains(")")){ //has () ==> METHODS !!
                                    
                            methodsName = codeArr_splitByBlank[2].substring(1, codeArr_splitByBlank[2].indexOf('(')); //starts from the word after -/+ to the word before '('                   
                            
                            if(methodsName.startsWith("set")){ //is SETTER !!
                                // " : +setOwner(String owner) void\n" ==> {"", ":", "+setOwner(String", "owner)", "void"}
                                //                                          0    1    2                   3         4
                                parameters = codeArr_splitByBlank[2].substring(codeArr_splitByBlank[2].indexOf('(')) + " " + codeArr_splitByBlank[3];
                                returnType = codeArr_splitByBlank[4];

                                String methodsName2;
                                methodsName2 = methodsName.substring(3); //get the name after "set"
                                methodsName2 = methodsName2.toLowerCase(); //deal the CamelCase

                                content += "\n    public " + returnType + " " + methodsName + parameters + " {\n        this." + methodsName2 + " = " + methodsName2 + ";\n    }";
                            }
                            else if(methodsName.startsWith("get")){ //is GETTER !!
                                // " : +getOwner() String\n" ==> {"", ":", "+getOwner()", "String"}
                                //                                0    1    2             3
                                parameters = "()";
                                returnType = codeArr_splitByBlank[3];

                                String methodsName2;
                                methodsName2 = methodsName.substring(3); //get the name after "get"
                                methodsName2 = methodsName2.toLowerCase(); //deal the CamelCase

                                content += "\n    public " + returnType + " " + methodsName + parameters + " {\n        return " + methodsName2 + ";\n    }";
                            }
                            else{ //other methods
                                // " : +isEnough(int value, int balance) void\n" ==> {"", ":", "+isEnough(int", "value,", "int", "balance)", "void"}
                                //                                                    0    1     2                3         4      5           6          
                                parameters = parameters + codeArr_splitByBlank[2].substring(codeArr_splitByBlank[2].indexOf('('));
                                for(int j=3; j<codeArr_splitByBlank.length-1; j++){
                                    
                                    parameters += " ";
                                    parameters += codeArr_splitByBlank[j];

                                }
                                returnType = codeArr_splitByBlank[codeArr_splitByBlank.length-1];
                                
                                if(returnType.equals("int")){
                                    content += "\n    public " + returnType + " " + methodsName + parameters + " {return 0;}";
                                }
                                else if(returnType.equals("String")){
                                    content += "\n    public " + returnType + " " + methodsName + parameters + " {return \"\";}";
                                }
                                else if(returnType.equals("boolean")){
                                    content += "\n    public " + returnType + " " + methodsName + parameters + " {return false;}";
                                }
                                else if(returnType.equals("void")){
                                    content += "\n    public " + returnType + " " + methodsName + parameters + " {;}";
                                }
                            }
                        }
                        else{ //atributes
                            // " : -int age\n" ==> {"", ":", "-int", "age"}
                            //                      0    1     2       3
                            type = codeArr_splitByBlank[2].substring(1);
                            atributesName = codeArr_splitByBlank[3];
                            content +=  "\n    public " + type + " " + atributesName + ";";
                        }
                    }     
                }
                content += "\n}";
                
                try {
                    String output = className + ".java";
                    File file = new File(output);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                        bw.write(content);
                    }
                    System.out.println(output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else{ //only class
                content += "\n}";

                try {
                    String output = className + ".java";
                    File file = new File(output);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                        bw.write(content);
                    }
                    System.out.println(output);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class FileReader {
    
    public String read(String fileName) {
        
        String readCode = "";
        try {
            readCode = Files.readString(Paths.get(fileName));
        }
        catch (IOException e) {
            //System.err.println("無法讀取文件 " + fileName);
            e.printStackTrace();
            //return;
        }

        return readCode;
    }
    
}

class Parser {
    
    public String[] splitByClass(String input){
        //List<String> merMaidCodeList = new ArrayList<>();
        String[] splitedCodeArr = new String[10];
        splitedCodeArr = (input.split("class"));

        return splitedCodeArr;
    }
}