package com.nutricionista.agendamento.controller;
import com.nutricionista.agendamento.model.Agendamento;
import com.nutricionista.agendamento.repository.AgendamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RestController
@RequestMapping("/api/agendamentos")
public class AgendamentoController {

    @Autowired
    private AgendamentoRepository agendamentoRepository;

    @GetMapping
    public List<Agendamento> getAllAgendamentos() {
        return agendamentoRepository.findAll();
    }

    @PostMapping
    public Agendamento createAgendamento(@RequestBody Agendamento agendamento) {
        return agendamentoRepository.save(agendamento);
    }

    @GetMapping("/{id}")
    public Agendamento getAgendamentoById(@PathVariable Long id) {
        return agendamentoRepository.findById(id).orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));
    }

    @PutMapping("/{id}")
    public Agendamento updateAgendamento(@PathVariable Long id, @RequestBody Agendamento agendamentoDetails) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        agendamento.setNomePaciente(agendamentoDetails.getNomePaciente());
        agendamento.setEmailPaciente(agendamentoDetails.getEmailPaciente());
        agendamento.setDataHora(agendamentoDetails.getDataHora());
        agendamento.setObservacoes(agendamentoDetails.getObservacoes());

        return agendamentoRepository.save(agendamento);
    }

    @DeleteMapping("/{id}")
    public void deleteAgendamento(@PathVariable Long id) {
        agendamentoRepository.deleteById(id);
    }

    @Configuration
    public class CorsConfig {
        @Bean
        public WebMvcConfigurer corsConfigurer() {
            return new WebMvcConfigurer() {
                @Override
                public void addCorsMappings(CorsRegistry registry) {
                    registry.addMapping("/**")
                            .allowedOrigins("http://localhost:5173") // Ajuste a porta do frontend
                            .allowedMethods("GET", "POST", "PUT", "DELETE")
                            .allowCredentials(true);
                }
            };
        }
    }
}
