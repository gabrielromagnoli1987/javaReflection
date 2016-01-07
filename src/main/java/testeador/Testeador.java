package testeador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
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
		
		// hardcodeada del paciente y el contexto sin aplicar reflection 
		// (para respetar el formato de entrada de datos que necesita el metodo create de la clase NotificacionDAO y no 
		// complicarme mas la existencia)
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
			
			// inicio codigo del chino - http://www.mkyong.com/webservices/jax-rs/restfull-java-client-with-java-net-url/
			
			OutputStream os = connection.getOutputStream();
			os.write(json.getBytes("UTF-8"));
			os.flush();
			
			if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
				throw new RuntimeException("Failed : HTTP error code : " + connection.getResponseCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((connection.getInputStream())));
			
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			connection.disconnect();
			
			// fin del codigo choreado del chino - si no entraste nunca en la pagina del chino no tuviste infancia
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

}
