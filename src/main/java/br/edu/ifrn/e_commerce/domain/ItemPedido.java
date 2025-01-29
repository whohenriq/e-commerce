package br.edu.ifrn.e_commerce.domain;

import java.math.BigDecimal;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "item_pedido")
public class ItemPedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Integer quantidade;
    private BigDecimal valorUnitario;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "produto_id")
    private Produto produto;
}
