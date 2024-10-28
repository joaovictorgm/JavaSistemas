package com.senac.mini.banco.controllers;

import com.senac.mini.banco.model.Cliente;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.senac.mini.banco.repositories.ClienteRepository;
import com.senac.mini.banco.DTOS.ClienteRequestDTO;
import com.senac.mini.banco.DTOS.ClienteResponseDTO;
import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("clientes")
public class ClienteController {

    //@autowired
    private final  ClienteRepository clienteRepository;

    public ClienteController(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //Receber parãmetros
    @GetMapping
    public <Page<ClienteResponseDTO>> findAll(@RequestParam(name = "numeroPagina", required = false, defaultValue = "0")int numeroPagina, @RequestParam(name = "quantidade", required = false, defaultValue = "5") int quantidade) {
        PageRequest pageRequest = PageRequest.of(numeroPagina, quantidade);
        return ResponseEntity.ok(clienteRepository.findAll(pageRequest).map(cliente -> new ClienteResponseDTO(cliente.getId(),cliente.getNome(), cliente.getEmail()));
    }

    @GetMapping("{id}")
    //Buscar por chave primária -- GET
    //Clientes/1 GET
    public ResponseEntity<Cliente>findById(@PathVariable("id") Integer id) {
        Optional<Cliente> cliente = clienteRepository.findById(id);

        if (cliente.isPresent()) {
            return ResponseEntity.ok(clienteRepository.get(pageRequest).map(cliente -> new ClienteResponseDTO(cliente.getId)));
        } else {
            throw new EntityNotFoundException("Cliente não encontrado");
        }
    }
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> save(@RequestBody ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNome(dto.nome());
        cliente.setEmail(dto.email());
        cliente.setContatoAdicional(dto.contatoAdicional());
        cliente.setLimiteCredito(dto.limmiteCredito());
        cliente.setProfissao(dto.profissao());
        cliente.setTelefoneContato(dto.telefoneContato());
        cliente.setDataNascimento(dto.dataNascimento());
        clienteRepository .save(cliente);
        return ResponseEntity.create(URI.create("/clientes/" + cliente.getId)).body(new ClienteResponseDTO(cliente.getId(). cliente.getNome(), cliente.getEmail()));
    }

    // Atualização - PUT

    @PutMapping("{id}")
    public ResponseEntity <Cliente> update(@PathVariable("id") Integer id, @RequestBody Cliente clienteRequisicao){
        Optional <Cliente> clienteOpt = clienteRepository.findById(id);

        if (clienteOpt.isPresent()) {
            Cliente clienteSalvo = clienteOpt.get();
            clienteSalvo.setNome(clienteRequisicao.getNome());

            return ResponseEntity.ok(clienteRepository.save(clienteSalvo));
        } else {
            throw new EntityNotFoundException("Cliente não encontrado.");
        }
    }

    //Exclusão -- DELETE
    @DeleteMapping("{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id")Integer id ) {
    clienteRepository.deleteById(id);
    }
}
