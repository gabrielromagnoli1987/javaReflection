package model;

import java.util.Date;

import annotations.Mock;
import annotations.MockStringAttribute;
import annotations.MockTodayAttribute;

@Mock
public class Notificacion {
	
	@MockStringAttribute({"asd","zxc"})
	private String text;
	
	@MockTodayAttribute
	private Date fechaEnvio;
	@MockTodayAttribute
	private Date fechaRecepcion;
	
	private Paciente paciente;
	private Contexto contexto;

	public Notificacion(String text, Date date) {
		super();
		this.text = text;
		this.fechaEnvio = date;
		this.fechaRecepcion = date;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Date getFechaEnvio() {
		return fechaEnvio;
	}
	
	public void setFechaEnvio(Date fechaEnvio) {
		this.fechaEnvio = fechaEnvio;
		
	}
	
	public Date getFechaRecepcion() {
		return fechaRecepcion;
	}
	
	public void setFechaRecepcion(Date fechaRecepcion) {
		this.fechaRecepcion = fechaRecepcion;
		
	}
	
	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public Contexto getContexto() {
		return contexto;
	}

	public void setContexto(Contexto contexto) {
		this.contexto = contexto;
	}

	@Override
	public String toString() {
		return this.getText() + " - " + this.getFechaEnvio();
	}

}
