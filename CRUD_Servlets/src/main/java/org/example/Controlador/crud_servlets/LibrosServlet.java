package org.example.Controlador.crud_servlets;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.Modelo.DAOLibro;
import org.example.Modelo.Libro;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "biblioteca", value = "/biblioteca")
public class LibrosServlet extends HttpServlet {

    DAOLibro daoLibro;
    ObjectMapper conversorJson;

    public void init(){ daoLibro = new DAOLibro();  conversorJson= new ObjectMapper(); }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");

        PrintWriter out = response.getWriter();

        conversorJson.registerModule(new JavaTimeModule());

        String accion = request.getParameter("accionCrud");
        String isbn = request.getParameter("isbn");
        String titulo = request.getParameter("titulo");
        String autor = request.getParameter("autor");
        switch (accion){
            case "Mostrar libros": mostrarLibros(out); break;
            case "Mostrar libro": mostrarLibro(isbn, out); break;
            case "Crear libro": crearLibro(isbn, titulo, autor, out); break;
            case "Actualizar libro": actualizarLibro(isbn, titulo, autor, out); break;
            case "Borrar libro": borrarLibro(isbn, out); break;
            default: break;
        }
    }

    public void mostrarLibros(PrintWriter out) throws IOException {
        List<Libro> libros = daoLibro.getAll();
        String json_response = conversorJson.writeValueAsString(libros);
        out.println(json_response);
    }

    public void mostrarLibro(String isbn, PrintWriter out) throws IOException {
        Libro libro = daoLibro.getById(isbn);
        String json_response = conversorJson.writeValueAsString(libro);
        out.println(json_response);
    }

    public void crearLibro(String isbn, String titulo, String autor, PrintWriter out) throws IOException {
        Libro libro = new Libro(isbn, titulo, autor);
        daoLibro.insert(libro);
        String json_response = conversorJson.writeValueAsString(libro);
        out.println(json_response);
    }

    public void actualizarLibro(String isbn, String titulo, String autor, PrintWriter out) throws IOException {
        Libro libro = daoLibro.getById(isbn);
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        daoLibro.update(libro);
        String json_response = conversorJson.writeValueAsString(libro);
        out.println(json_response);
    }

    public void borrarLibro(String isbn, PrintWriter out) throws IOException {
        Libro libro = daoLibro.getById(isbn);
        daoLibro.delete(libro);
        out.println("Eliminado correctamente");
    }
}
