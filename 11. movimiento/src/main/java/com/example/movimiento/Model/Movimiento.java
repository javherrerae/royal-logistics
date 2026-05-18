package com.example.movimiento.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
// Con persistence cada microservicio tendrá su propia base de datos, guardamos los objetos creados en Java
//  permanentemente en la base de datos
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movimiento")
public class Movimiento {

    // Usamos Long porque el ID es autogenerado por la base de datos
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idMovimiento;

    @NotNull(message = "El ID de desconsolidación es obligatorio")
    @Column(nullable = false)
    private Long idDesconsolidacion;

    @NotBlank(message = "El SKU es obligatorio")
    @Column(nullable = false, length = 20)
    private String sku;

    @NotBlank(message = "El ID de ubicación es obligatorio")
    @Column(nullable = false, length = 6)
    private String idUbicacion;

    @NotBlank(message = "El tipo de movimiento es obligatorio")
    @Column(nullable = false, length = 30)
    private String tipoMovimiento;

    // Usamos Integer para permitir validaciones con @NotNull
    @NotNull(message = "La cantidad es obligatoria")
    @Column(nullable = false)
    private Integer cantidad;

    @NotBlank(message = "El destino es obligatorio")
    @Column(nullable = false, length = 50)
    private String destino;
}
/*
{
  "idDesconsolidacion": 1,
  "sku": "SKU12345",
  "idUbicacion": "U001",
  "tipoMovimiento": "INGRESO",
  "cantidad": 10,
  "destino": "RACK"
}
*/