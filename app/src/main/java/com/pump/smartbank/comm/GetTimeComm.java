package com.pump.smartbank.comm;

import java.net.Socket;
import java.util.Vector;

public class GetTimeComm extends BaseComm 
{
	private static final short FUNC_REQ_GET_TIME=106;
	private static final short FUNC_RET_GET_TIME=8106;

	private int year; 
	private int month;
	private int day;
	private int hour;
	private int minute;
	private int second;

	public GetTimeComm(Socket socket) 
	{
		super(socket, FUNC_REQ_GET_TIME, FUNC_RET_GET_TIME);
	}

	@Override
	protected Vector<Byte> MakePackBody() 
	{
		Vector<Byte> data = new Vector<Byte>();
		data.clear();
		return data;
	}

	@Override
	protected int fetchData(byte[] data) 
	{
		int result = bytestous(data);
		if (result != 0)
		{
			return -6;
		}
		year = data[2]+1900;
		month = data[3];
		day = data[4];
		hour = data[5];
		minute = data[6];
		second = data[7];
		return 0;
	}
	
	public String getTime()
	{
		return String.format("%04d-%02d-%02d %02d:%02d:%02d", 
				year, month, day, hour, minute, second);
	}
}
