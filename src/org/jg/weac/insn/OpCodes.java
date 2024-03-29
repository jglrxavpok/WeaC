package org.jg.weac.insn;

import java.util.*;

public interface OpCodes
{

	public static final int		 VAR_LOAD	 = 0x0;
	public static final int		 METHOD_CALL  = 0x1;
	public static final int		 LOAD_CONST   = 0x2;
	public static final int		 METHOD_START = 0x3;
	public static final int		 METHOD_END   = 0x4;
	public static final int		 RETURN	   = 0x5;
	public static final int		 OPERATION	= 0x6;
	public static final int		 LINE_NUMBER  = 0x7;
	public static final int		 VAR_STORE	= 0x8;
	public static final int		 LABEL		= 0x9;
	public static final int		 IF		   = 0xA;
	public static final int		 LOAD_NULL	= 0xB;
	public static final int		 INVERT_BOOL  = 0xC;
	public static final int		 GOTO		 = 0xD;
	public static final int		 ARRAY_STORE  = 0xE;
	public static final int		 NEW_ARRAY	= 0xF;

	public static final int		 PUSH_ARRAY   = 0x10;
	public static final int		 GET_FIELD	= 0x11;
	public static final int		 LOAD_INDEX   = 0x12;

	static HashMap<Integer, String> nameMap	  = new HashMap<>();

	public static String toString(int opcode)
	{
		if(nameMap.isEmpty())
		{
			nameMap.put(VAR_LOAD, "VAR_LOAD");
			nameMap.put(METHOD_CALL, "METHOD_CALL");
			nameMap.put(LOAD_CONST, "LOAD_CONST");
			nameMap.put(METHOD_START, "METHOD_START");
			nameMap.put(METHOD_END, "METHOD_END");
			nameMap.put(RETURN, "RETURN");
			nameMap.put(OPERATION, "OPERATION");
			nameMap.put(LINE_NUMBER, "LINE_NUMBER");
			nameMap.put(VAR_STORE, "VAR_STORE");
			nameMap.put(LABEL, "LABEL");
			nameMap.put(IF, "IF");
			nameMap.put(LOAD_NULL, "LOAD_NULL");
			nameMap.put(INVERT_BOOL, "INVERT_BOOL");
			nameMap.put(GOTO, "GOTO");
			nameMap.put(ARRAY_STORE, "ARRAY_STORE");
			nameMap.put(NEW_ARRAY, "NEW_ARRAY");
			nameMap.put(PUSH_ARRAY, "PUSH_ARRAY");
			nameMap.put(GET_FIELD, "GET_FIELD");
			nameMap.put(LOAD_INDEX, "LOAD_INDEX");
		}
		if(!nameMap.containsKey(opcode))
		{
			return opcode + "";
		}
		return nameMap.get(opcode);
	}

}
