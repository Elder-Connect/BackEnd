package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "tb_proposals", schema = "elder_ly")
@SequenceGenerator(name = "sq_co_proposal", sequenceName = "seq_co_proposal", allocationSize = 1, initialValue = 1, schema = "elder_ly")
public class Proposta implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "sq_co_proposal", strategy = GenerationType.SEQUENCE)
    @Column(name = "co_proposal")
    private Long id;

    @Column(name = "description")
    private String descricao;

    @Column(name = "day_start_time")
    private LocalDateTime dataHoraInicio;

    @Column(name = "day_time_end")
    private LocalDateTime dataHoraFim;

    @Column(name = "price")
    private BigDecimal preco;

    @Column(name = "accepted")
    private Boolean aceita;

    @OneToOne(cascade = CascadeType.ALL) @JoinColumn(name = "message_id")
    private Mensagem mensagem;
}
