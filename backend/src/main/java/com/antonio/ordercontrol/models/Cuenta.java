package com.antonio.ordercontrol.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.annotations.ColumnDefault;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "cuenta", schema = "restaurante_tfg")
public class Cuenta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_mesa", nullable = false)
    private Mesa idMesa;

    @ColumnDefault("0.00")
    @Column(name = "suma_total", precision = 10, scale = 2)
    private BigDecimal sumaTotal;

    @Column(name = "hora_cobro")
    private Instant horaCobro;

    @Column(name = "metodo_pago", length = 50)
    private String metodoPago;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Mesa getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(Mesa idMesa) {
        this.idMesa = idMesa;
    }

    public BigDecimal getSumaTotal() {
        return sumaTotal;
    }

    public void setSumaTotal(BigDecimal sumaTotal) {
        this.sumaTotal = sumaTotal;
    }

    public Instant getHoraCobro() {
        return horaCobro;
    }

    public void setHoraCobro(Instant horaCobro) {
        this.horaCobro = horaCobro;
    }

    public String getMetodoPago() {
        return metodoPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

}