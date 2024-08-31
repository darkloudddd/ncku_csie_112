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
    public int _iMemberCount = 0;

//#define MEMBER_NUM_MAX	20
	public static final int MEMBER_NUM_MAX = 30;

    public boolean[] _abIsPublic = new boolean[MEMBER_NUM_MAX];
    public EnuMemberFuncType[] _aeMemberFunctionType = new EnuMemberFuncType[MEMBER_NUM_MAX]; // 0:atribute   1:setter    2:getter    3:others
    public String[] _aStrMemberType = new String[MEMBER_NUM_MAX];
    public String[] _aStrMemberName = new String[MEMBER_NUM_MAX];
    public String[] _aStrMemberName_2 = new String[MEMBER_NUM_MAX]; // Only for function set/get

	ClsInfo() {
		//System.out.println("ClsInfo() - init");
		_iMemberCount = 0;
	}

	/*public void setClsName(String strClassName)
	{
		_strClsName = strClassName;
	}*/

	public void AddMember( boolean bIsPublic, EnuMemberFuncType eMemberFunction, String strMemberType, String strMemberName, String strMemberName_2 )
	{
		//System.out.printf("AddMember(%b %d %s %s %s)\n", bIsPublic, eMemberFunction, strMemberType, strMemberName, strMemberName_2);
		//System.out.printf("AddMember(%b)\n", bIsPublic); // core dump
		System.out.printf("AddMember( ");
		if( bIsPublic )
			System.out.printf("true");
		else
			System.out.printf("false");
		System.out.printf(" %s %s [%s] %s)\n", eMemberFunction, strMemberType, strMemberName, strMemberName_2);

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
		//System.out.println("ClsMermaidInfo() - init");
		_iClassCount = 0;
	}

	public ClsInfo AddClass(String strClsName)
	{
		System.out.printf("AddClass(%s)\n", strClsName);

		ClsInfo pClsInfo = FindClass(strClsName);

		if( pClsInfo != null )
			return pClsInfo;

		pClsInfo = new ClsInfo();

		pClsInfo._strClsName = strClsName;

		_aClsInfo[_iClassCount] = pClsInfo;

		_iClassCount += 1;

		System.out.printf("AddClass(%s) -- end, _iClassCount: %d\n", strClsName, _iClassCount);

		return pClsInfo;
	}

	public ClsInfo FindClass(String strClsName) {
		//ClsInfo pClsInfo = null;

		for (int iClsIdx = 0; iClsIdx < _iClassCount; iClsIdx++) {
			if (_aClsInfo[iClsIdx]._strClsName.equals(strClsName)) {
				return _aClsInfo[iClsIdx];
			}
		}
		return null;
	}
}

public class mermaid_reader {

	public static final int MEMBER_FUNC_NOT = 0;
	public static final int MEMBER_FUNC_SET = 1;
	public static final int MEMBER_FUNC_GET = 2;
	public static final int MEMBER_FUNC_OTHER = 3;


	public static ClsMermaidInfo _clsMermaidInfo = new ClsMermaidInfo();
	//public ClsMermaidInfo _clsMermaidInfo = new ClsMermaidInfo();
	//public ClsMermaidInfo _clsMermaidInfo;

	mermaid_reader() {
		//System.out.println("mermaid_reader() - init");
	}

	void read_file_test()
	{
		System.out.printf("read_file_test()\n");

		// import class 1...
		ClsInfo pCurClsInfo = _clsMermaidInfo.AddClass("BankAccount");
		//System.out.printf("_iClassCount:%d\n", _clsMermaidInfo._iClassCount);

		// import class 1-member 1 ...
		pCurClsInfo.AddMember( false, EnuMemberFuncType.NOT_FUNC, "int", "owner", "" );

		// import class 1-member 2 ...
		pCurClsInfo.AddMember( false, EnuMemberFuncType.NOT_FUNC, "int", "balance", "" );

		// import class 1-member 3 ...
		pCurClsInfo.AddMember( true, EnuMemberFuncType.FUNC_SET, "void", "setOwner(String owner)", "owner" );

		// import class 1-member 4 ...
		pCurClsInfo.AddMember( true, EnuMemberFuncType.FUNC_OTHER, "void", "isEnough(int value, int balance)", "" );

		// import class 1-member 5 ...
		pCurClsInfo.AddMember( true, EnuMemberFuncType.FUNC_GET, "void", "getOwner(String owner)", "owner" );
	}

	void read_file(String strFileName)
	{
		System.out.printf("read_file(%s)\n", strFileName);
		int iLineNum = 0;

        try {
            BufferedReader reader = new BufferedReader(new FileReader(strFileName));
            String line;
			//String line_tmp;

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

				iLineNum += 1;

				//if( iLineNum >= 21 )
				//	break;

				if( line.length() == 0 )
				{
					continue;
				}

				System.out.printf("\n(%d, %d) [%s]\n", iLineNum, line.length(), line);

		        StringTokenizer st_line = new StringTokenizer( line, " ");

		        // Condition holds true till there is single token
		        // remaining using hasMoreTokens() method
		        iLineTokenCount = 0;
		        while( st_line.hasMoreTokens() && (iLineTokenCount<20) )
		        {
		            // Getting next tokens
		            aStrLineToken[iLineTokenCount] = st_line.nextToken();
		            System.out.printf("[%s]", aStrLineToken[iLineTokenCount]);
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
                            //clsInfoArr[idxofClsInfoArr].classNamestr = classname;
                            //idxofClsInfoArr++;
                            pCurClsInfo = _clsMermaidInfo.AddClass(classname);
                            continue;
                        }
                        else{
                            String classname = (line.substring(5)).trim();
                            //clsInfoArr[idxofClsInfoArr].classNamestr = classname;
                            //idxofClsInfoArr++;
                            pCurClsInfo = _clsMermaidInfo.AddClass(classname);
                            continue;
                        }

                    }
                    else if( line.contains("+") || line.contains("-") ) { //defining member
                        //clsInfoArr[idxofClsInfoArr].memberCount++;

						// Check this case: class_name : +-...
						if( line.contains(":") ) {
							//iOffsetOf_colon = line.indexOf(":");
							//System.out.printf("iOffsetOf_colon=%d\n", iOffsetOf_colon);

							// todo: need check class name
							//System.out.printf("aStrLineToken[1]=%s\n", aStrLineToken[1]);
							//if( aStrLineToken[1] == ":" )
							if( aStrLineToken[1].equals(":") )
							{
								//System.out.printf("aStrLineToken[1] == :\n");
								//System.out.printf("aStrLineToken[0]=%s\n", aStrLineToken[0]);
								// Change current class
								pCurClsInfo = _clsMermaidInfo.FindClass(aStrLineToken[0]);
							}
						}
						else {
							//iOffsetOf_colon = -1;
						}

                        //if( line.startsWith("-")){ //Private
                        if( line.contains("-")) { //Private
                            //clsInfoArr[idxofClsInfoArr].isPublicArr[idxofisPublicArr] = false;
                            //idxofisPublicArr++;
                            iOffsetOf_P_M = line.indexOf("-");
							bIsPublic = false;
                        }
                        //else if ( line.startsWith("+")){ //Public
                        else if ( line.contains("+")) { //Public
                            //clsInfoArr[idxofClsInfoArr].isPublicArr[idxofisPublicArr] = true;
                            //idxofisPublicArr++;
                            iOffsetOf_P_M = line.indexOf("+");
                            bIsPublic = true;
                        }
						else
						{
							System.out.printf("Invalid case!!!!!!!!!!!!!!!!!!!!!!!!\n" );
							break;
						}
						//System.out.printf("iOffsetOf_P_M=%d\n", iOffsetOf_P_M);

                        if(line.contains("(") && line.contains(")")) { //methods

                            //clsInfoArr[idxofClsInfoArr].memberName[idxofmemberNameArr] = line.substring(1, line.indexOf("(")); //store member name
                            //idxofmemberName2Arr = idxofmemberNameArr;
                            //idxofmemberNameArr++;
                            //strMemberName = line.substring(1, line.indexOf("(")); //store member name
                            strMemberName = line.substring(iOffsetOf_P_M + 1, line.indexOf(")") + 1 ); //store member name
                            System.out.printf("strMemberName => [%s]\n", strMemberName );

                            //if( clsInfoArr[idxofClsInfoArr].memberName[idxofmemberNameArr].startsWith("set")) { //setter
                            if( strMemberName.startsWith("set") ) { //setter
                                //clsInfoArr[idxofClsInfoArr].memberFunctionType[idxofmemberFunctionTypeArr] = 1;
                                eMemberFuncType = EnuMemberFuncType.FUNC_SET;
                                //idxofmemberFunctionTypeArr++;

                                //store the name after "set" to memberName2[]
                                //clsInfoArr[idxofClsInfoArr].memberName2[idxofmemberName2Arr] = ((clsInfoArr[idxofClsInfoArr].memberName[idxofmemberNameArr]).substring(3)).toLowerCase();
                                strMemberName_2 = strMemberName.substring(3, strMemberName.indexOf("(") ).toLowerCase();
								System.out.printf("strMemberName_2 => [%s]\n", strMemberName_2 );
                                //idxofmemberName2Arr++;
                            }
                            //else if(clsInfoArr[idxofClsInfoArr].memberName[idxofmemberNameArr].startsWith("get")){ //getter
                            else if( strMemberName.startsWith("get") ) { //getter
                                //clsInfoArr[idxofClsInfoArr].memberFunctionType[idxofmemberFunctionTypeArr] = 2;
                                eMemberFuncType = EnuMemberFuncType.FUNC_GET;
                                //idxofmemberFunctionTypeArr++;

                                //store the name after "get" to memberName2[]
                                //clsInfoArr[idxofClsInfoArr].memberName2[idxofmemberName2Arr] = ((clsInfoArr[idxofClsInfoArr].memberName[idxofmemberNameArr]).substring(3)).toLowerCase();
								strMemberName_2 = strMemberName.substring(3, strMemberName.indexOf("(") );
								//System.out.printf("strMemberName_2 => [%s]\n", strMemberName_2 );
								strMemberName_2 = strMemberName_2.toLowerCase();
								System.out.printf("strMemberName_2 => [%s]\n", strMemberName_2 );

                                //idxofmemberName2Arr++;
                            }
                            else { //others
                                //clsInfoArr[idxofClsInfoArr].memberFunctionType[idxofmemberFunctionTypeArr] = 3; //others
                                //idxofmemberFunctionTypeArr++;
								eMemberFuncType = EnuMemberFuncType.FUNC_OTHER;
								strMemberName_2 = "none";
                            }

                            //clsInfoArr[idxofClsInfoArr].memberType[idxofmemberTypeArr] = (line.substring(line.indexOf(")"+1))).trim(); //store member type
                            //idxofmemberTypeArr++;

							strMemberType = (line.substring(line.indexOf(")")+1)).trim(); //store member type
							System.out.printf("strMemberType => [%s]\n", strMemberType );

							// Add member to class
							pCurClsInfo.AddMember( bIsPublic, eMemberFuncType, strMemberType, strMemberName, strMemberName_2 );

                        }
                        else { //atributes
							String strTmp = line.substring( iOffsetOf_P_M + 1);
							System.out.printf("strTmp => [%s]\n", strTmp );

							// Use string StringTokenizer
					       	// Creating object of class inside main() method
					        StringTokenizer st1 = new StringTokenizer( strTmp, " ");

					        // Condition holds true till there is single token
					        // remaining using hasMoreTokens() method
					        //while (st1.hasMoreTokens())
					            // Getting next tokens
					            //System.out.println(st1.nextToken());

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

                            //clsInfoArr[idxofClsInfoArr].memberName[idxofmemberNameArr] = line.substring(line.lastIndexOf(" ")+1); //store member name
                            //idxofmemberNameArr++;

                            //clsInfoArr[idxofClsInfoArr].memberFunctionType[idxofmemberFunctionTypeArr] = 0;
                            //idxofmemberFunctionTypeArr++;

                            //clsInfoArr[idxofClsInfoArr].memberType[idxofmemberTypeArr] = line.substring(1, line.indexOf(" ")); //store member type
                            //idxofmemberTypeArr++;

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


		System.out.println("output_file() .....");
		System.out.println("=====================================================");
		System.out.printf("// Total class count: %d\n", _clsMermaidInfo._iClassCount);

	    for (classIndex = 0; classIndex < _clsMermaidInfo._iClassCount; classIndex += 1) {

			String strFileContent = "";

	        //System.out.printf("public class %s {\n", _clsMermaidInfo._aClsInfo[classIndex]._strClsName);
			strFileContent += String.format("public class %s {\n", _clsMermaidInfo._aClsInfo[classIndex]._strClsName);

	        //System.out.printf("// member count: %d\n", _clsMermaidInfo._aClsInfo[classIndex]._iMemberCount);

	        for (memberIndex = 0; memberIndex < _clsMermaidInfo._aClsInfo[classIndex]._iMemberCount; memberIndex += 1) {
	            //System.out.printf("    ");
				strFileContent += "    ";

	            if (_clsMermaidInfo._aClsInfo[classIndex]._abIsPublic[memberIndex]) {
	                //System.out.printf("public ");
					strFileContent += "public ";
	            } else {
	                //System.out.printf("private ");
					strFileContent += "private ";
	            }

	            if (_clsMermaidInfo._aClsInfo[classIndex]._aeMemberFunctionType[memberIndex] != EnuMemberFuncType.NOT_FUNC ) { // is function
	                /*System.out.printf("%s %s {",
	                        _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberType[memberIndex],
	                        _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName[memberIndex]); */
					strFileContent += String.format("%s %s {",
	                        _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberType[memberIndex],
	                        _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName[memberIndex]);

	                switch (_clsMermaidInfo._aClsInfo[classIndex]._aeMemberFunctionType[memberIndex]) {
	                    // case 1: set
	                    case EnuMemberFuncType.FUNC_SET: {
	                        /*System.out.printf("\n");
	                        System.out.printf("        this.%s = %s;\n",
	                                _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName_2[memberIndex],
	                                _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName_2[memberIndex]);
	                        System.out.printf("    }");*/

							strFileContent += "\n";
							strFileContent += String.format("        this.%s = %s;\n",
	                                _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName_2[memberIndex],
	                                _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName_2[memberIndex]);
							strFileContent += "    }";
	                        break;
	                    }
	                    // case 2: get
	                    case EnuMemberFuncType.FUNC_GET:
	                        /*System.out.printf("\n");
	                        System.out.printf("        return %s;\n", _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName_2[memberIndex]);
	                        System.out.printf("    }"); */

							strFileContent += "\n";
							strFileContent += String.format("        return %s;\n", _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName_2[memberIndex]);
							strFileContent += "    }";
	                        break;
	                    // case 3: other
	                    case EnuMemberFuncType.FUNC_OTHER:
	                    default:
	                        //System.out.printf(";}");
							strFileContent += ";}";
	                        break;
	                }

	            }
				else { // is variable
	                /*System.out.printf("%s %s;",
	                        _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberType[memberIndex],
	                        _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName[memberIndex]);*/
					strFileContent += String.format("%s %s;",
	                        _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberType[memberIndex],
	                        _clsMermaidInfo._aClsInfo[classIndex]._aStrMemberName[memberIndex]);
	            }

	            //System.out.printf("\n");
				strFileContent += "\n";
	        }

	        //System.out.printf("}\n");
			strFileContent += "}\n";

			System.out.printf("\n+++++++++++++++++++++++++++++++++++++++++++\n");
			System.out.printf(strFileContent);
			System.out.printf("---------------------------------------------\n");

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
                System.out.println(strOutFileName);
            } catch (IOException e) {
                e.printStackTrace();
            }

	    }

	}

    public static void main(String[] args) {
        System.out.println("Hello Victor~");

		if( args.length > 0)
			System.out.printf("p1=%s, \n", args[0]);

		mermaid_reader reader = new mermaid_reader();

		//reader.read_file_test();

		if( args.length > 0)
			reader.read_file(args[0]);

		reader.output_file();

    }
}
