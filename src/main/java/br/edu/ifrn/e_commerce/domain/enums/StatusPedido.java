package br.edu.ifrn.e_commerce.domain.enums;

public enum StatusPedido {
    AGUARDANDO("Aguardando pagamento"),
    CANCELADO("Cancelar Pedido"),
    PAGO("Pago"),
    ENVIADO("Enviado");

    private String status;

    StatusPedido(String status) {
        this.status = status;
    }

    public String getStatus() {
        return this.status;
    }
}
