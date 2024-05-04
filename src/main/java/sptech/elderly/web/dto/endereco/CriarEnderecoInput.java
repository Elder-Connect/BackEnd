package sptech.elderly.web.dto.endereco;

import jakarta.validation.constraints.NotBlank;
import sptech.elderly.entity.Endereco;

import java.io.Serializable;

/**
 * DTO for {@link Endereco}
 */
public record CriarEnderecoInput(@NotBlank String cep,
                                 @NotBlank String logradouro,
                                 @NotBlank String numero,
                                 @NotBlank String complemento,
                                 @NotBlank String uf,
                                 @NotBlank String cidade,
                                 @NotBlank String bairro) implements Serializable {
}