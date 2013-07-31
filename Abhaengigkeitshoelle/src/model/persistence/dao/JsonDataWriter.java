package model.persistence.dao;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import com.google.gson.Gson;

import model.http.crawler.dataconverter.Result;
import model.http.urlconnection.ConnectionFactory;

public class JsonDataWriter implements DataWriter {
	private ConnectionFactory conFac;
	private URL host;

	public JsonDataWriter(URL host, ConnectionFactory conFac) {
		this.conFac = conFac;
		this.host = host;
	}
	@Override
	public void add(URL origin, Result data) {
		try {
			URLConnection connection = this.conFac.establishConnection(host);
			OutputStream outputStream = connection.getOutputStream();
			outputStream.write(
					new Gson().toJson(data).getBytes("UTF-8"));
			outputStream.flush();
			outputStream.close();
			connection.connect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Result get(URL origin) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<URL> ls() {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public boolean contains(URL origin) {
		// TODO Auto-generated method stub
		return false;
	}

}
