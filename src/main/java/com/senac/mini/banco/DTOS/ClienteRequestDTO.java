package com.senac.mini.banco.DTOS;

import java.time.LocalDate;

public record ClienteRequestDTO(String nome, LocalDate dataNascimento, String email, String telefoneContato, String contatoAdicional, String profissao, double limiteCredito) {
}
