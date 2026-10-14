package com.krakedev.asistencias.services;

import java.util.ArrayList;
import org.springframework.stereotype.Service;
import com.krakedev.asistencias.entidades.Estudiante;

@Service
public class ServicioEstudiantes {
    private ArrayList<Estudiante> estudiantes = new ArrayList<>();

    public void agregar(Estudiante estudiante) {
        if (buscarPorCedula(estudiante.getCedula()) == null) {
            estudiantes.add(estudiante);
        }
    }

    public Estudiante buscarPorCedula(String cedula) {
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getCedula().equals(cedula)) {
                return estudiante;
            }
        }
        return null;
    }

    public void eliminar(String cedula) {
        Estudiante estudiante = buscarPorCedula(cedula);
        if (estudiante != null) {
            estudiantes.remove(estudiante);
        }
    }

    public void actualizar(String cedula, Estudiante nuevo) {
        Estudiante estudiante = buscarPorCedula(cedula);
        if (estudiante != null) {
            estudiante.setNombre(nuevo.getNombre());
            estudiante.setApellido(nuevo.getApellido());
        }
    }

    public ArrayList<Estudiante> listar() {
        return estudiantes;
    }
}
