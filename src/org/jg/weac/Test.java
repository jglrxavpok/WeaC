package org.jg.weac;

import java.io.*;

public class Test
{

	public static void main(String[] args)
	{
		String source = read("/test." + WeaCHelper.IMPL_EXTENSION);
		String headers = read("/test." + WeaCHelper.HEADER_EXTENSION);
		try
		{
			new WeaCompiler().compileAndRun(headers, source, "test");
		}
		catch(WeaCException e)
		{
			e.printStackTrace();
		}
	}

	private static String read(String file)
	{
		try
		{
			InputStream input = Test.class.getResourceAsStream(file);
			byte[] buffer = new byte[65565];
			int i;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			while((i = input.read(buffer, 0, buffer.length)) != -1)
			{
				out.write(buffer, 0, i);
			}
			out.flush();
			out.close();
			return new String(out.toByteArray(), "UTF-8");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
}
