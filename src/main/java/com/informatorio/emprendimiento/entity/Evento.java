package com.informatorio.emprendimiento.entity;

import com.informatorio.emprendimiento.enums.EstadoEvento;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Table(name = ("evento"))
public class Evento {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    @Column(name = "idEvento")
    private Long idEvento;

    @NotBlank(message = " no debe estar en blanco")
    @Getter @Setter
    @Column(name = "detallesEvento", length = 500)
    private String detallesEvento;
    @Getter @Setter
    @CreationTimestamp
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "fechaCreacion")
    private Date fechaCreacion;
    @Getter @Setter
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "fechaCierre")
    private Date fechaCierre;
    @Setter @Column(name = "estado")
    private EstadoEvento estadoEvento;
    @NotNull(message="no puede estar en blanco.")
    @Enumerated(EnumType.STRING)
    public EstadoEvento getEstadoEvento() {
        return estadoEvento;
    }
    @Getter @Setter @Column(name = "premio")
    @NotNull(message = "no puede estar vacio.")
    private BigInteger premio;
    public Evento(String detallesEvento, Date fechaCierre, EstadoEvento estadoEvento, BigInteger premio){
        this.detallesEvento = detallesEvento;
        this.fechaCierre = fechaCierre;
        this.estadoEvento = estadoEvento;
        this.premio = premio;
    }
    public Evento() {

    }
}