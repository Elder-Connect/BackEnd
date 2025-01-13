package sptech.elderly.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter @Setter @NoArgsConstructor @EqualsAndHashCode
@Entity @Table(name = "tb_resumes", schema = "elder_ly")
@SequenceGenerator(name = "sq_co_resume", sequenceName = "seq_co_resume", allocationSize = 1, initialValue = 1, schema = "elder_ly")
public class Curriculo implements Serializable {

    /**
    *
    * */

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "sq_co_resume", strategy = GenerationType.SEQUENCE)
    @Column(name = "co_resume")
    private Long id;

    @ManyToOne @JoinColumn(name = "user_id")
    private Usuario usuario;

    @ManyToOne @JoinColumn(name = "specialtie_id")
    private Especialidade especialidade;
}
