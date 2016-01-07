package testeador;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.google.gson.Gson;

import model.Contexto;
import model.Notificacion;
import model.Paciente;

public class Testeador {

	public static void main(String[] args) {
		
		Gson gson = new Gson();
		
		List<Notificacion> notificaciones = MockGenerator.createMockInstances(Notificacion.class, 50);
		
		// hardcodeada del paciente y el contexto sin reflection 
		// (para respetar el formato de entrada de datos que necesita el metodo create de la clase NotificacionDAO)
		Paciente paciente = new Paciente("nombre", "apellido", 88888888);
		paciente.setId(1);
		Contexto contexto = new Contexto("cxt", "desc");
		contexto.setId(1);
		
		for (Notificacion notificacion : notificaciones) {
			notificacion.setPaciente(paciente);
			notificacion.setContexto(contexto);
		}
		// fin hardcodeada
		
		
		String json = gson.toJson(notificaciones);
		
		System.out.println(json);
		
		String targetURL = "http://localhost:7777/hermes/";
		
		try {
			URL url = new URL(targetURL);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/json");
			connection.setDoOutput(true);
						
			OutputStreamWriter sw = new OutputStreamWriter(connection.getOutputStream());
			sw.write(json);
			sw.close();
			
//			OutputStream os = connection.getOutputStream();
//			os.write(json.getBytes("UTF-8"));			
//			os.close();
			
//			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
//			wr.writeBytes(json);
//			wr.close();
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
