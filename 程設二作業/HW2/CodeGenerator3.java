import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;

// Java String Tokenizer Constructors
import java.util.*;


// mermaid class diagram

enum EnuMemberFuncType {
	NOT_FUNC,
	FUNC_SET,
	FUNC_GET,
	FUNC_OTHER
}

class ClsInfo {
    public String _strClsName = "Unknown";
    public int _iMemberCount;

	//#define MEMBER_NUM_MAX	20
	public static final int MEMBER_NUM_MAX = 60;

    public boolean[] _abIsPublic = new boolean[MEMBER_NUM_MAX];
    public EnuMemberFuncType[] _aeMemberFunctionType = new EnuMemberFuncType[MEMBER_NUM_MAX]; // 0:atribute   1:setter    2:getter    3:others
    public String[] _aStrMemberType = new String[MEMBER_NUM_MAX];
    public String[] _aStrMemberName = new String[MEMBER_NUM_MAX];
    public String[] _aStrMemberName_2 = new String[MEMBER_NUM_MAX]; // Only for function set/get

	ClsInfo() {
		_iMemberCount = 0;
	}
	
	public void AddMember( boolean bIsPublic, EnuMemberFuncType eMemberFunction, String strMemberType, String strMemberName, String strMemberName_2 )
	{
		_abIsPublic[_iMemberCount] = bIsPublic;
		_aeMemberFunctionType[_iMemberCount] = eMemberFunction;
		_aStrMemberType[_iMemberCount] = strMemberType;
		_aStrMemberName[_iMemberCount] = strMemberName;
		_aStrMemberName_2[_iMemberCount] = strMemberName_2;

		_iMemberCount += 1;
	}
}

class ClsMermaidInfo
{
	public int _iClassCount;

	public static final int CLASS_NUM_MAX = 10;

	public ClsInfo[] _aClsInfo = new ClsInfo[CLASS_NUM_MAX];

	ClsMermaidInfo() {
		_iClassCount = 0;
	}

	public ClsInfo AddClass(String strClsName)
	{
		ClsInfo pClsInfo = FindClass(strClsName);

		if( pClsInfo != null )
			return pClsInfo;

		pClsInfo = new ClsInfo();

		pClsInfo._strClsName = strClsName;

		_aClsInfo[_iClassCount] = pClsInfo;

		_iClassCount += 1;

		return pClsInfo;
	}

	public ClsInfo FindClass(String strClsName) {

		for (int iClsIdx = 0; iClsIdx < _iClassCount; iClsIdx++) {
			if (_aClsInfo[iClsIdx]._strClsName.equals(strClsName)) {
				return _aClsInfo[iClsIdx];
			}
		}
		return null;
	}
}

public class CodeGenerator {

	public static final int MEMBER_FUNC_NOT = 0;
	public static final int MEMBER_FUNC_SET = 1;
	public static final int MEMBER_FUNC_GET = 2;
	public static final int MEMBER_FUNC_OTHER = 3;


	public static ClsMermaidInfo _clsMermaidInfo = new ClsMermaidInfo();

	public static String toString(char[] a)
    {
        String string = new String(a);
        return string;
    }
	
	void read_file(String strFileName)
	{
        try {
            BufferedReader reader = new BufferedReader(new FileReader(strFileName));
            String line;

			ClsInfo pCurClsInfo = null;

			boolean bIsPublic;
			EnuMemberFuncType eMemberFuncType;
			String strMemberType;
			String strMemberName;
			String strMemberName_2;

			int iOffsetOf_P_M; // offset for '+' '-'
			//int iOffsetOf_colon; // offset for ':'

			int iLineTokenCount;
			String[] aStrLineToken = new String[20];

            while ((line = reader.readLine()) != null) {

                line = line.trim(); //remove all the blank at the begin and the end

				if( line.length() == 0 )
				{
					continue;
				}

		        StringTokenizer st_line = new StringTokenizer( line, " ");

		        // Condition holds true till there is single token
		        // remaining using hasMoreTokens() method
		        iLineTokenCount = 0;
		        while( st_line.hasMoreTokens() && (iLineTokenCount<20) )
		        {
		            // Getting next tokens
		            aStrLineToken[iLineTokenCount] = st_line.nextToken();
		            //System.out.printf("[%s]", aStrLineToken[iLineTokenCount]);
					iLineTokenCount += 1;
		        }
				System.out.printf("\n");

                if(line.equals("classDiagram")){
                    continue;
                }
                else if(line.equals("}")){ //only "}"
                    continue;
                }
                else { //start

                    if(line.startsWith("class")){ //defining class

                        if(line.contains("{")){
                            String classname = (line.substring(5, line.indexOf("{"))).trim();
                            pCurClsInfo = _clsMermaidInfo.AddClass(classname);
                            continue;
                        }
                        else{
                            String classname = (line.substring(5)).trim();
                            pCurClsInfo = _clsMermaidInfo.AddClass(classname);
                            continue;
                        }
                    }
                    else if( line.contains("+") || line.contains("-") ) { //defining member

						// Check this case: class_name : +-...
						if( line.contains(":") ) {
							
							if( aStrLineToken[1].equals(":") )
							{
								pCurClsInfo = _clsMermaidInfo.FindClass(aStrLineToken[0]);
							}
						}

                        if( line.contains("-")) { //Private
                            
                            iOffsetOf_P_M = line.indexOf("-");
							bIsPublic = false;
                        }
                        else if ( line.contains("+")) { //Public

                            iOffsetOf_P_M = line.indexOf("+");
                            bIsPublic = true;
                        }
						else
						{
							System.out.printf("Invalid case!!!!!!!!!!!!!!!!!!!!!!!!\n" );
							break;
						}

                        if(line.contains("(") && line.contains(")")) { //methods

                            strMemberName = line.substring(iOffsetOf_P_M + 1, line.indexOf(")") + 1 ); //store member name

                            if( strMemberName.startsWith("set") ) { //setter

                                eMemberFuncType = EnuMemberFuncType.FUNC_SET;
                                
                                strMemberName_2 = strMemberName.substring(3, strMemberName.indexOf("(") );
								
								if(Character.isUpperCase(strMemberName_2.charAt(0))){
									char[] memberName_2Arr = strMemberName_2.toCharArray();
									memberName_2Arr[0] = Character.toLowerCase(memberName_2Arr[0]);
									strMemberName_2 = toString(memberName_2Arr);
								}
                            }
                            else if( strMemberName.startsWith("get") ) { //getter

                                eMemberFuncType = EnuMemberFuncType.FUNC_GET;
                                
                                strMemberName_2 = strMemberName.substring(3, strMemberName.indexOf("(") );
								
								if(Character.isUpperCase(strMemberName_2.charAt(0))){
									char[] memberName_2Arr = strMemberName_2.toCharArray();
									memberName_2Arr[0] = Character.toLowerCase(memberName_2Arr[0]);
									strMemberName_2 = toString(memberName_2Arr);
								}
                            }
                            else { //other methods
								eMemberFuncType = EnuMemberFuncType.FUNC_OTHER;
								strMemberName_2 = "none";
                            }

							strMemberType = (line.substring(line.indexOf(")")+1)).trim(); //store member type

							// Add member to class
							pCurClsInfo.AddMember( bIsPublic, eMemberFuncType, strMemberType, strMemberName, strMemberName_2 );

                        }
                        else { //atributes
							String strTmp = line.substring( iOffsetOf_P_M + 1);

							// Use string StringTokenizer
					       	// Creating object of class inside main() method
					        StringTokenizer st1 = new StringTokenizer( strTmp, " ");

					        // Condition holds true till there is single token
					        // remaining using hasMoreTokens() method
					        // Get str-1 => variable type
					        if( st1.hasMoreTokens() )
				        	{
				        		strMemberType = st1.nextToken();

								// Get str-2 => variable name
						        if( st1.hasMoreTokens() )
					        	{
					        		strMemberName = st1.nextToken();

									eMemberFuncType = EnuMemberFuncType.NOT_FUNC; // store function type

									// Add member to class
									pCurClsInfo.AddMember( bIsPublic, eMemberFuncType, strMemberType, strMemberName, "" );
					        	}
				        	}
                        }
                    }
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
	}

	void output_file()
	{
		int classIndex = 0;
		int memberIndex = 0;

	    for (classIndex = 0; classIndex < _clsMermaidInfo._iClassCount; classIndex += 1) {

			String strFileContent = "";

			strFileContent += String.format("public class %s {\n", _clsMermaidInfo._aClsInfo[classIndex]._strClsName);

	        for (memberIndex = 0; memberIndex < _clsMermaidInfo._aClsInfo[classIndex]._iMemberCount; memberIndex += 1) {

				strFileContent += "    ";

	            if (_clsMermaidInfo._aClsInfo[classIndex]._abIsPublic[memberIndex]) {
					strFileContent += "public ";
	            } else {
					strFileContent += "private ";
	            }

	            if (_clsMermaidInfo._aClsInfo[classIndex]._aeMemberFunctionType[memberIndex] != EnuMemberFuncType.NOT_FUNC ) { // is function

					if(_clsMermaidInfo._aClsInfo[classIndex]._aStrMemberType[memberIndex].equals("")){
						strFileContent += String.format("%s %s {",
	                        "void",
	                        _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName[memberIndex]);
					}
					else{
						strFileContent += String.format("%s %s {",
	                        _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberType[memberIndex],
	                        _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName[memberIndex]);
					}

	                switch (_clsMermaidInfo._aClsInfo[classIndex]._aeMemberFunctionType[memberIndex]) {
	                    
                        // case 1: set
	                    case FUNC_SET: {

							strFileContent += "\n";
							strFileContent += String.format("        this.%s = %s;\n",
	                                _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName_2[memberIndex],
	                                _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName_2[memberIndex]);
							strFileContent += "    }";
	                        break;
	                    }
	                    // case 2: get
	                    case FUNC_GET:

							strFileContent += "\n";
							strFileContent += String.format("        return %s;\n", _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName_2[memberIndex]);
							strFileContent += "    }";
	                        break;
	                    // case 3: other
	                    case FUNC_OTHER:
                            if(_clsMermaidInfo._aClsInfo[classIndex]._aStrMemberType[memberIndex].equals("int")){
                                strFileContent += "return 0;}";
                            }
                            else if(_clsMermaidInfo._aClsInfo[classIndex]._aStrMemberType[memberIndex].equals("boolean")){
                                strFileContent += "return false;}";
                            }
                            else if(_clsMermaidInfo._aClsInfo[classIndex]._aStrMemberType[memberIndex].equals("String")){
                                strFileContent += "return \"\";}";
                            }
                            else if(_clsMermaidInfo._aClsInfo[classIndex]._aStrMemberType[memberIndex].equals("void")){
                                strFileContent += ";}";
                            }
							else if(_clsMermaidInfo._aClsInfo[classIndex]._aStrMemberType[memberIndex].equals("")){
								strFileContent += ";}";
							}
                            break;
	                    
                        default:
							break;
	                }
	            }
				else { // is variable
					strFileContent += String.format("%s %s;",
	                        _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberType[memberIndex],
	                        _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName[memberIndex]);
	            }
				strFileContent += "\n";
	        }

			strFileContent += "}\n";

			// Write to file
			try {
                String strOutFileName = _clsMermaidInfo._aClsInfo[classIndex]._strClsName + ".java";
                File file = new File(strOutFileName);
                if (!file.exists()) {
                    file.createNewFile();
                }
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
                    bw.write(strFileContent);
                }
                //System.out.println(strOutFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

	    }

	}

    public static void main(String[] args) {

		CodeGenerator reader = new CodeGenerator();

		if( args.length > 0)
			reader.read_file(args[0]);

		reader.output_file();

    }
}
