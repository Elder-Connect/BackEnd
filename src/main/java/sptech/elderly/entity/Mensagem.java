package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor
@Entity @Table(name = "tb_messages", schema = "elder_ly")
@SequenceGenerator(name = "sq_co_message", sequenceName = "seq_co_message", allocationSize = 1, initialValue = 1, schema = "elder_ly")
public class Mensagem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "sq_co_message", strategy = GenerationType.SEQUENCE)
    @Column(name = "co_message")
    private Long id;

    @Column(name = "content")
    private String conteudo;

    @Column(name = "date_time")
    private LocalDateTime dataHora;

    @ManyToOne(cascade = CascadeType.ALL) @JoinColumn(name = "recipient_id")
    private Usuario destinatario;

    @ManyToOne(cascade = CascadeType.ALL) @JoinColumn(name = "sender_id")
    private Usuario remetente;

    @OneToOne(mappedBy = "mensagem", fetch = FetchType.LAZY)
    private Proposta proposta;
}
