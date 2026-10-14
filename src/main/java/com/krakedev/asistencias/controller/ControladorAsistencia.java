package com.krakedev.asistencias.controller;

import java.util.ArrayList;
import org.springframework.web.bind.annotation.*;
import com.krakedev.asistencias.entidades.Asistencia;
import com.krakedev.asistencias.entidades.RegistroAsistencia;
import com.krakedev.asistencias.services.ServicioAsistencia;

@RestController
@RequestMapping("/asistencias")
public class ControladorAsistencia {
    private final ServicioAsistencia servicioAsistencia;

    public ControladorAsistencia(ServicioAsistencia servicioAsistencia) {
        this.servicioAsistencia = servicioAsistencia;
    }

    @PostMapping("/{cedula}")
    public RegistroAsistencia registrarAsistencia(@PathVariable("cedula") String cedula) {
        return servicioAsistencia.registrarAsistencia(cedula);
    }

    @GetMapping("/{cedula}")
    public ArrayList<Asistencia> consultarAsistencia(@PathVariable("cedula") String cedula) {
        return servicioAsistencia.consultarAsistencia(cedula);
    }
}
