package ar.com.ada.creditos.entities;

import javax.persistence.*;

import org.hibernate.annotations.NaturalId;

import ar.com.ada.creditos.excepciones.*;

import java.util.*;

@Entity
@Table(name = "cliente")
public class Cliente {
    @Id
    @Column(name = "cliente_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int clienteId;

    private String nombre;

    @NaturalId
    private int dni;

    private String direccion;

    @Column(name = "direccion_alternativa")
    private String direccionAlternativa;

    @Column(name = "fecha_nacimiento")
    @Temporal(TemporalType.DATE) // SOLO Poner esto si no queremos manejar HORA en el DB Server.
    private Date fechaNacimiento;

    @OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Prestamo> prestamos = new ArrayList<>();

    public Cliente(String nombre) {
        this.nombre = nombre;

    }

    public Cliente() {
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) throws ClienteNombreException {
        if (nombre.isEmpty()) {
            throw new ClienteNombreException(this, "Indique un nombre");
        }
        this.nombre = nombre;
    }

    public void setDni(int dni) throws ClienteDNIException {

        if (dni < 0) {
            // no se ejecuta nada mas despues del throw
            throw new ClienteDNIException(this, "ocurrio un error con el DNI");

        }
        this.dni = dni;
    }

    public int getDni() {
        return dni;
    }

    @Override
    public String toString() {
        return "Cliente [id=" + clienteId + ", dni=" + dni + ", nombre=" + nombre + "]";
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDireccionAlternativa() {
        return direccionAlternativa;
    }

    public void setDireccionAlternativa(String direccionAlternativa) {
        this.direccionAlternativa = direccionAlternativa;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public List<Prestamo> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(List<Prestamo> prestamos) {
        this.prestamos = prestamos;
    }

    public void agregarPrestamo(Prestamo prestamo) {
        if (!prestamos.contains(prestamo)) {
            prestamos.add(prestamo);
            prestamo.setCliente(this);

        }

    }
}
