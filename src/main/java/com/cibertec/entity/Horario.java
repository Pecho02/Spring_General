package com.cibertec.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Horario {
	private int idHorario;
	private String fechaInicio;
	private String fechaFin;
	private String dia;
	private String horaInicio;
	private String horaFin;
	private String horas;
	private int idCurso;
	private Curso curso;
	private int estudiantes;
}
