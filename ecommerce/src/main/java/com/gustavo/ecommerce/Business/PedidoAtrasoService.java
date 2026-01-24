package com.gustavo.ecommerce.Business;

import com.gustavo.ecommerce.entity.Pedido;
import com.gustavo.ecommerce.entity.enums.StatusPedido;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Set;

@Service
public class PedidoAtrasoService {

    private static final Set<StatusPedido> STATUS_VALIDOS = Set.of(
            StatusPedido.AGUARDANDO,
            StatusPedido.EM_PREPARO,
            StatusPedido.SAIU_PARA_ENTREGA
    );

    private static final long LIMITE_MINUTOS = 50;

    public boolean isPedidoAtrasado(Pedido pedido) {

        if (!STATUS_VALIDOS.contains(pedido.getStatus())) {
            return false;
        }

        LocalDateTime inicio = pedido.getDataCriacao();

        long minutos = Duration.between(inicio, LocalDateTime.now()).toMinutes();

        return minutos > LIMITE_MINUTOS;
    }
}
