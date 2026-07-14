package br.com.stockFlow.controller;

import br.com.stockFlow.Model.Movimentation;
import br.com.stockFlow.dto.PostMovimentationDTO;
import br.com.stockFlow.dto.ResponseMovimentationDTO;
import br.com.stockFlow.mapper.MovimentationMapper;
import br.com.stockFlow.service.MovimentationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor

@RestController
@RequestMapping("/mov")
public class MovimentationController {

    private MovimentationService service;
    private MovimentationMapper mapper;

    @PostMapping
    public ResponseEntity<ResponseMovimentationDTO> insertMovimentation(@RequestBody PostMovimentationDTO dto) {
        Movimentation movimentation = mapper.PostDTOtoEntity(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDTO(service.save(movimentation)));
    }

    @GetMapping
    public List<ResponseMovimentationDTO>findAll() {
      return mapper.toDTOList(service.findAll()) ;
    }

    @GetMapping("{id}")
    public ResponseEntity<ResponseMovimentationDTO> findById(@PathVariable UUID id) {
        return ResponseEntity.ok(mapper.toDTO(service.findById(id)));
    }
}
