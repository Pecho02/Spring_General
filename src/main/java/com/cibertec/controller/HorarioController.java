package com.cibertec.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.cibertec.entity.Curso;
import com.cibertec.entity.Horario;

@Controller
@RequestMapping("/rest/curso/general")
public class HorarioController {
	// PostgreSQL
	String URL_CURSO = "http://localhost:8091/rest/curso/lista";

	// MongoDB
	String URL_HORARIO = "http://localhost:8093/rest/horario/lista";
	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/listar")
	public ResponseEntity<?> registrarSede() {
		HashMap<String, Object> salida = new HashMap<>();

		ResponseEntity<List<Curso>> responseEntityCurso = restTemplate.exchange(URL_CURSO, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Curso>>() {
				});
		List<Curso> lstCurso = responseEntityCurso.getBody();

		ResponseEntity<List<Horario>> responseEntityHorario = restTemplate.exchange(URL_HORARIO, HttpMethod.GET, null,
				new ParameterizedTypeReference<List<Horario>>() {
				});
		List<Horario> lstHorarios = responseEntityHorario.getBody();

		// Crear una nueva lista de horarios con los datos del curso correspondiente

		List<Horario> lstHorariosConCurso = new ArrayList<>();
		for (Horario horario : lstHorarios) {
			int idCurso = horario.getIdCurso();
			Curso cursoCorrespondiente = buscarCursoPorId(lstCurso, idCurso);
			if (cursoCorrespondiente != null) {
				horario.setCurso(cursoCorrespondiente);
				lstHorariosConCurso.add(horario);
			}
		}
		salida.put("Horarios", lstHorarios);
		return ResponseEntity.ok(salida);
	}

	// Método auxiliar para buscar un curso por su id en la lista de cursos
	private Curso buscarCursoPorId(List<Curso> lstCurso, int idCurso) {
		for (Curso curso : lstCurso) {
			if (curso.getIdCurso() == idCurso) {
				return curso;
			}
		}
		return null;
	}
}
