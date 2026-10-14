package com.krakedev.asistencias;

import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.krakedev.asistencias.entidades.*;
import com.krakedev.asistencias.services.*;

class ServiciosTest {
    private ServicioEstudiantes estudiantes;
    private ServicioAsistencia asistencias;

    @BeforeEach
    void preparar() {
        estudiantes = new ServicioEstudiantes();
        asistencias = new ServicioAsistencia(estudiantes);
    }

    @Test
    void listarEstudiantesInicialmenteVacio() {
        assertTrue(estudiantes.listar().isEmpty());
    }

    @Test
    void agregarYBuscarEstudiante() {
        Estudiante estudiante = new Estudiante("123", "Dylan", "Ramon");
        estudiantes.agregar(estudiante);
        assertEquals(1, estudiantes.listar().size());
        assertSame(estudiante, estudiantes.buscarPorCedula("123"));
    }

    @Test
    void noAgregarCedulaRepetida() {
        estudiantes.agregar(new Estudiante("123", "Dylan", "Ramon"));
        estudiantes.agregar(new Estudiante("123", "Otro", "Apellido"));
        assertEquals(1, estudiantes.listar().size());
        assertEquals("Dylan", estudiantes.buscarPorCedula("123").getNombre());
    }

    @Test
    void buscarEstudianteInexistente() {
        assertNull(estudiantes.buscarPorCedula("999"));
    }

    @Test
    void actualizarConservaCedulaYAsistencias() {
        estudiantes.agregar(new Estudiante("123", "Dylan", "Ramon"));
        RegistroAsistencia registro = asistencias.registrarAsistencia("123");
        estudiantes.actualizar("123", new Estudiante("999", "Paul", "Castillo"));
        Estudiante actualizado = estudiantes.buscarPorCedula("123");
        assertEquals("123", actualizado.getCedula());
        assertEquals("Paul", actualizado.getNombre());
        assertEquals("Castillo", actualizado.getApellido());
        assertNull(estudiantes.buscarPorCedula("999"));
        assertSame(actualizado, registro.getEstudiante());
        assertEquals(1, asistencias.consultarAsistencia("123").size());
    }

    @Test
    void actualizarInexistenteNoAgregaEstudiante() {
        estudiantes.actualizar("999", new Estudiante("999", "Ana", "Perez"));
        assertTrue(estudiantes.listar().isEmpty());
    }

    @Test
    void eliminarSoloElEstudianteIndicado() {
        estudiantes.agregar(new Estudiante("123", "Dylan", "Ramon"));
        estudiantes.agregar(new Estudiante("456", "Ana", "Perez"));
        estudiantes.eliminar("123");
        assertNull(estudiantes.buscarPorCedula("123"));
        assertEquals(1, estudiantes.listar().size());
        assertNotNull(estudiantes.buscarPorCedula("456"));
    }

    @Test
    void eliminarInexistenteNoAfectaLista() {
        estudiantes.agregar(new Estudiante("123", "Dylan", "Ramon"));
        estudiantes.eliminar("999");
        assertEquals(1, estudiantes.listar().size());
    }

    @Test
    void registrarAsistenciaPresenteConFechaYHoraActuales() {
        Estudiante estudiante = new Estudiante("123", "Dylan", "Ramon");
        estudiantes.agregar(estudiante);
        LocalDateTime antes = LocalDateTime.now();
        RegistroAsistencia registro = asistencias.registrarAsistencia("123");
        LocalDateTime despues = LocalDateTime.now();
        assertSame(estudiante, registro.getEstudiante());
        Asistencia asistencia = registro.getAsistencia();
        assertEquals("P", asistencia.getEstado());
        assertFalse(asistencia.getFechaHoraRegistro().isBefore(antes));
        assertFalse(asistencia.getFechaHoraRegistro().isAfter(despues));
        assertFalse(asistencia.getFechaClase().isBefore(antes.toLocalDate()));
        assertFalse(asistencia.getFechaClase().isAfter(despues.toLocalDate()));
    }

    @Test
    void registrarEstudianteInexistenteRetornaNull() {
        assertNull(asistencias.registrarAsistencia("999"));
        assertTrue(asistencias.consultarAsistencia("999").isEmpty());
    }

    @Test
    void consultarTodasLasAsistenciasSoloDelEstudiante() {
        estudiantes.agregar(new Estudiante("123", "Dylan", "Ramon"));
        estudiantes.agregar(new Estudiante("456", "Ana", "Perez"));
        RegistroAsistencia primero = asistencias.registrarAsistencia("123");
        asistencias.registrarAsistencia("456");
        RegistroAsistencia segundo = asistencias.registrarAsistencia("123");
        assertEquals(2, asistencias.consultarAsistencia("123").size());
        assertSame(primero.getAsistencia(), asistencias.consultarAsistencia("123").get(0));
        assertSame(segundo.getAsistencia(), asistencias.consultarAsistencia("123").get(1));
        assertEquals(1, asistencias.consultarAsistencia("456").size());
    }

    @Test
    void consultarEstudianteSinAsistencias() {
        estudiantes.agregar(new Estudiante("123", "Dylan", "Ramon"));
        assertTrue(asistencias.consultarAsistencia("123").isEmpty());
    }

    @Test
    void noRegistrarNuevaAsistenciaDespuesDeEliminarEstudiante() {
        estudiantes.agregar(new Estudiante("123", "Dylan", "Ramon"));
        asistencias.registrarAsistencia("123");
        estudiantes.eliminar("123");
        assertNull(asistencias.registrarAsistencia("123"));
        assertEquals(1, asistencias.consultarAsistencia("123").size());
    }

    @Test
    void constructoresYPropiedadesDeEntidades() {
        Estudiante estudiante = new Estudiante();
        estudiante.setCedula("456");
        estudiante.setNombre("Ana");
        estudiante.setApellido("Perez");
        Asistencia asistencia = new Asistencia();
        asistencia.setFechaClase(LocalDate.of(2026, 4, 28));
        asistencia.setFechaHoraRegistro(LocalDateTime.of(2026, 4, 28, 10, 30));
        asistencia.setEstado("A");
        RegistroAsistencia registro = new RegistroAsistencia();
        registro.setEstudiante(estudiante);
        registro.setAsistencia(asistencia);
        assertEquals("456", registro.getEstudiante().getCedula());
        assertEquals("Ana", estudiante.getNombre());
        assertEquals("Perez", estudiante.getApellido());
        assertEquals("A", registro.getAsistencia().getEstado());
        assertEquals(LocalDate.of(2026, 4, 28), asistencia.getFechaClase());
        assertEquals(LocalDateTime.of(2026, 4, 28, 10, 30), asistencia.getFechaHoraRegistro());
        assertTrue(estudiante.toString().contains("456"));
        assertTrue(asistencia.toString().contains("2026-04-28"));
        assertTrue(registro.toString().contains("456"));
    }
}
