package br.edu.ifrn.e_commerce.domain.enums;

public enum StatusPedido {
    AGUARDANDO("Aguardando pagamento"),
    CANCELADO("Cancelar Pedido"),
    PAGO("Pago"),
    ENVIADO("Enviado");

    private final String descricao;

    StatusPedido(String descricao) {
        this.descricao = descricao;
    }

    public String getStatus() {
        return this.descricao;
    }
}
