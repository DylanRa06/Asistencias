package com.krakedev.asistencias;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.krakedev.asistencias.controller.ControladorEstudiantes;
import com.krakedev.asistencias.controller.ControladorAsistencia;
import com.krakedev.asistencias.entidades.Estudiante;
import com.krakedev.asistencias.entidades.RegistroAsistencia;
import com.krakedev.asistencias.services.ServicioEstudiantes;

@SpringBootTest
class AsistenciasApplicationTests {
    @Autowired
    private ControladorEstudiantes controladorEstudiantes;
    @Autowired
    private ControladorAsistencia controladorAsistencia;
    @Autowired
    private ServicioEstudiantes servicioEstudiantes;

    @Test
    void serviciosCompartenEstudiantesMedianteInyeccion() {
        Estudiante estudiante = new Estudiante("prueba-spring", "Ana", "Perez");
        controladorEstudiantes.agregar(estudiante);
        assertSame(estudiante, servicioEstudiantes.buscarPorCedula("prueba-spring"));
        RegistroAsistencia registro = controladorAsistencia.registrarAsistencia("prueba-spring");
        assertNotNull(registro);
        assertSame(estudiante, registro.getEstudiante());
        assertEquals(1, controladorAsistencia.consultarAsistencia("prueba-spring").size());
        controladorEstudiantes.eliminar("prueba-spring");
    }
}
