package com.krakedev.asistencias.controller;

import java.util.ArrayList;
import org.springframework.web.bind.annotation.*;
import com.krakedev.asistencias.entidades.Estudiante;
import com.krakedev.asistencias.services.ServicioEstudiantes;

@RestController
@RequestMapping("/estudiantes")
public class ControladorEstudiantes {
    private final ServicioEstudiantes servicioEstudiantes;

    public ControladorEstudiantes(ServicioEstudiantes servicioEstudiantes) {
        this.servicioEstudiantes = servicioEstudiantes;
    }

    @PostMapping
    public void agregar(@RequestBody Estudiante estudiante) {
        servicioEstudiantes.agregar(estudiante);
    }

    @GetMapping("/{cedula}")
    public Estudiante buscarPorCedula(@PathVariable("cedula") String cedula) {
        return servicioEstudiantes.buscarPorCedula(cedula);
    }

    @GetMapping
    public ArrayList<Estudiante> listar() {
        return servicioEstudiantes.listar();
    }

    @PutMapping("/{cedula}")
    public void actualizar(@PathVariable("cedula") String cedula,
            @RequestBody Estudiante nuevo) {
        servicioEstudiantes.actualizar(cedula, nuevo);
    }

    @DeleteMapping("/{cedula}")
    public void eliminar(@PathVariable("cedula") String cedula) {
        servicioEstudiantes.eliminar(cedula);
    }
}
