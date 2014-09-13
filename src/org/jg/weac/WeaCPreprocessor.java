package org.jg.weac;

import java.util.*;

public class WeaCPreprocessor
{

	private HashMap<String, String> defined = new HashMap<>();

	public String parseHeader(WeaCompiler compiler, int i, String includeDirective, String oldImplementation, String headerName)
	{
		String requestedHeader = WeaCompiler.read("/" + headerName + "." + WeaCHelper.HEADER_EXTENSION);
		String[] lines = requestedHeader.split("\n");
		String header = "";
		boolean shouldInterpret = true;
		for(String line : lines)
		{
			if(line.startsWith("#"))
			{
				line = line.substring(1);
				if(line.startsWith("ifndef "))
				{
					String def = line.replaceFirst("ifndef ", "");
					while(def.charAt(0) == ' ')
						def = def.substring(1);
					if(!defined.containsKey(def))
					{
						defined.put(def, "");
					}
					else
						shouldInterpret = false;
				}
				else if(line.startsWith("endif"))
				{
					shouldInterpret = true;
				}
			}
			else if(shouldInterpret)
			{
				header += line;
				header += "\n";
			}
		}
		WeaCLib lib = compiler.parseLib(header, headerName);
		String requestedLib = "";
		if(lib != null)
		{
			compiler.include(lib);
			requestedLib = WeaCompiler.read("/" + headerName + "." + WeaCHelper.IMPL_EXTENSION);
		}
		int end = i - includeDirective.length() - 1;
		if(end < 0) end = 0;
		String implementation = oldImplementation.substring(0, end).replaceFirst(headerName, "") + requestedLib + "$$$$$$$$$$$$$$$$$" + oldImplementation.substring(i + 1);
		compiler.enterNewLib(headerName);
		return implementation;
	}
}
