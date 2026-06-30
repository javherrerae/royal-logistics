package com.example.autenticacion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.autenticacion.service.CredencialService;

import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Gestión de Credenciales", description = "Microservicio de credenciales. Pendiente de endpoints funcionales para documentación completa")
@RestController
@RequestMapping("/api/credenciales")
public class CredencialController {
    @Autowired
    private CredencialService credencialService;
}
