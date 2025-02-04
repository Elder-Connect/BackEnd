package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.Curriculo;
import sptech.elderly.entity.Especialidade;
import sptech.elderly.entity.Usuario;
import sptech.elderly.repository.CurriculoRepository;
import sptech.elderly.repository.EspecialidadeRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service @RequiredArgsConstructor
public class CurriculoService {

    private final CurriculoRepository curriculoRepository;
    private final EspecialidadeRepository especialidadeRepository;

    public void excluirUsuario(Long idUsuario) {
        curriculoRepository.deleteByUsuarioId(idUsuario);
    }

    public void excluirEspecialidade(Long idEspecialidade) {
        curriculoRepository.deleteByEspecialidadeId(idEspecialidade);
    }

    public List<Curriculo> associarColaboradorEspecialidade(Usuario usuario, List<Long> idEspecialidades) {
        excluirUsuario(usuario.getId());

        return idEspecialidades.stream()
                .map(idEspecialidade -> {

                    Especialidade especialidade = especialidadeRepository.findById(idEspecialidade)
                            .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Especialidade não encontrada."));

                    Curriculo curriculoExistente = curriculoRepository.findByUsuarioAndEspecialidade(usuario, especialidade);

                    if (curriculoExistente == null){
                        curriculoExistente = new Curriculo();
                        curriculoExistente.setUsuario(usuario);
                        curriculoExistente.setEspecialidade(especialidade);
                        curriculoRepository.save(curriculoExistente);
                    }

                    return curriculoExistente;
                })
                .collect(Collectors.toList());
    }
}
