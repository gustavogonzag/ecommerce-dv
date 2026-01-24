package com.gustavo.ecommerce.service.impl;

import com.gustavo.ecommerce.dto.request.BairroRequestDTO;
import com.gustavo.ecommerce.entity.Bairro;
import com.gustavo.ecommerce.exception.ResourceNotFoundException;
import com.gustavo.ecommerce.repository.BairroRepository;
import com.gustavo.ecommerce.service.BairroService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BairroServiceImpl implements BairroService {

    private final BairroRepository bairroRepository;

    public BairroServiceImpl(BairroRepository bairroRepository) {
        this.bairroRepository = bairroRepository;
    }

    @Override
    public BairroRequestDTO cadastroBairro(BairroRequestDTO dto) {

        Bairro bairro = new Bairro();
        bairro.setNome(dto.getNome());
        bairro.setTaxaEntrega(dto.getTaxaEntrega());

        Bairro bairroSalvo = bairroRepository.save(bairro);

        BairroRequestDTO dtoSalvo = new BairroRequestDTO();
        dtoSalvo.setId(bairroSalvo.getId());
        dtoSalvo.setNome(bairroSalvo.getNome());
        dtoSalvo.setTaxaEntrega(bairroSalvo.getTaxaEntrega());

        return dtoSalvo;
    }

    @Override
    public BairroRequestDTO atualizarBairro(BairroRequestDTO dto) {

        Bairro bairroExistente = bairroRepository.findById(dto.getId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Bairro não encontrado")
                );

        bairroExistente.setNome(dto.getNome());
        bairroExistente.setTaxaEntrega(dto.getTaxaEntrega());

        Bairro bairroAtualizado = bairroRepository.save(bairroExistente);

        BairroRequestDTO dtoNovo = new BairroRequestDTO();
        dtoNovo.setId(bairroAtualizado.getId());
        dtoNovo.setNome(bairroAtualizado.getNome());
        dtoNovo.setTaxaEntrega(bairroAtualizado.getTaxaEntrega());

        return dtoNovo;
    }

    @Override
    public List<BairroRequestDTO> listarBairros() {

        return bairroRepository.findAll()
                .stream()
                .map(bairro -> {
                    BairroRequestDTO res = new BairroRequestDTO();
                    res.setId(bairro.getId());
                    res.setNome(bairro.getNome());
                    res.setTaxaEntrega(bairro.getTaxaEntrega());
                    return res;
                })
                .collect(Collectors.toList());
    }

    @Override
    public void apagarBairro(Integer id) {

        if (!bairroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Bairro não encontrado");
        }

        bairroRepository.deleteById(id);
    }
}
