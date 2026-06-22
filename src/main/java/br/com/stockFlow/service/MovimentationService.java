package br.com.stockFlow.service;

import br.com.stockFlow.exception.InsufficientStockException;
import br.com.stockFlow.exception.NotFoundException;
import br.com.stockFlow.Model.Movimentation;
import br.com.stockFlow.Model.Product;
import br.com.stockFlow.enums.Constants;
import br.com.stockFlow.repository.MovimentationRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class MovimentationService {

    private final MovimentationRepository movimentationRepository;
    private final ProductService productService;

    @Transactional(readOnly = true)
    public Movimentation findById(UUID id) {
        return movimentationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(Constants.MSG_NOT_FOUND.getValue()));
    }

    @Transactional(readOnly = true)
    public List<Movimentation> findAll() {
        return movimentationRepository.findAll();
    }

    @Transactional
    public Movimentation save(Movimentation movimentation) {
        Product product = productService.findById(movimentation.getProduct().getId());
        int newQuantity = product.getQuantity() + movimentation.getQuantity();
        if (newQuantity < 0)
            throw new InsufficientStockException(Constants.MSG_INSUFFICIENT_STOCK.getValue());
        product.setQuantity(newQuantity);
        return movimentationRepository.save(movimentation);
    }
}
