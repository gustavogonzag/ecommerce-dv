package com.gustavo.ecommerce.service;

import com.gustavo.ecommerce.dto.request.BairroRequestDTO;

import java.util.List;

public interface BairroService {

    BairroRequestDTO cadastroBairro(BairroRequestDTO dto);
    BairroRequestDTO atualizarBairro(BairroRequestDTO dto);
    List<BairroRequestDTO> listarBairros();
    void apagarBairro(Integer id);

}
