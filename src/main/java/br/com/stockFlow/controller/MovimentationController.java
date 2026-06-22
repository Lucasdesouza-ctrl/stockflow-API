package br.com.stockFlow.controller;

import br.com.stockFlow.Model.Movimentation;
import br.com.stockFlow.dto.MovimentationDTO;
import br.com.stockFlow.mapper.MovimentationMapper;
import br.com.stockFlow.service.MovimentationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor

@RestController
@RequestMapping("/mov")
public class MovimentationController {

    private MovimentationService service;
    private MovimentationMapper mapper;

    @PostMapping
    public ResponseEntity<MovimentationDTO> insertMovimentation(@RequestBody MovimentationDTO dto) {
        Movimentation movimentation = mapper.toEntity(dto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(mapper.toDTO(service.save(movimentation)));
    }
}
