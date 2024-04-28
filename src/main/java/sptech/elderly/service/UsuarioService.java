package sptech.elderly.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import sptech.elderly.entity.*;
import sptech.elderly.repository.GeneroRepository;
import sptech.elderly.repository.TipoUsuarioRepository;
import sptech.elderly.repository.UsuarioRepository;
import sptech.elderly.web.dto.usuario.ClienteMapper;
import sptech.elderly.web.dto.usuario.CriarCliente;
import sptech.elderly.web.dto.usuario.CriarFuncionario;
import sptech.elderly.web.dto.usuario.FuncionarioMapper;

import java.util.List;

@Service @RequiredArgsConstructor
public class UsuarioService {


    private final UsuarioRepository usuarioRepository;

    private final FuncionarioMapper funcionarioMapper;

    private final ClienteMapper clienteMapper;

    private final GeneroRepository generoRepository;

    private final TipoUsuarioRepository tipoUsuarioRepository;

    private final ResidenciaService residenciaService;

    private final EnderecoService enderecoService;

    private final EspecialidadeService especialidadeService;

    private final CurriculoService curriculoService;

    public UsuarioEntity salvarCliente(CriarCliente novoCliente) {
        if (usuarioRepository.existsByEmail(novoCliente.email())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email ja cadastrado!");
        }

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(novoCliente.tipoUsuario())
                .orElseThrow(
                        () -> new RuntimeException("Tipo usuário não encontrado.")
                );

        Endereco endereco = enderecoService.salvar(novoCliente.endereco());

        UsuarioEntity novoUsuario = clienteMapper.criarCliente(novoCliente);
        novoUsuario.setTipoUsuario(tipoUsuarioId);

        novoUsuario = usuarioRepository.save(novoUsuario);

        residenciaService.salvar(novoUsuario, endereco);

        return novoUsuario;
    }

    public UsuarioEntity salvarFuncionario(CriarFuncionario novoFuncionario) {
        if (usuarioRepository.existsByEmail(novoFuncionario.email())) {
            throw new ResponseStatusException(HttpStatusCode.valueOf(409), "Email ja cadastrado!");
        }

        TipoUsuario tipoUsuarioId = tipoUsuarioRepository.findById(novoFuncionario.tipoUsuario())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Tipo usuário não encontrado.")
                );

        Genero generoId = generoRepository.findById(novoFuncionario.genero())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Gênero não encontrado.")
                );

        Endereco endereco = enderecoService.salvar(novoFuncionario.endereco());

        UsuarioEntity novoUsuario = funcionarioMapper.criarFuncionario(novoFuncionario);
        novoUsuario.setTipoUsuario(tipoUsuarioId);
        novoUsuario.setGenero(generoId);


        novoUsuario = usuarioRepository.save(novoUsuario);

        List<Especialidade> especialidades = especialidadeService.salvar(novoFuncionario.especialidades());

        for (Especialidade especialidade : especialidades) {
            curriculoService.associarEspecialidadeUsuario(novoUsuario, especialidade);
        }

        residenciaService.salvar(novoUsuario, endereco);
        return novoUsuario;
    }

//    @Transactional(readOnly = true)
//    public UsuarioConsultaDto buscarPorId(Integer userId) {
//        UsuarioEntity usuario = usuarioRepository.findById(userId).orElseThrow(
//                () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado.")
//        );
//
//        return FuncionarioMapper.toDto(usuario);
//    }
//
//    public List<UsuarioEntity> buscarUsuarios() {
//        // Buscar todos os usuários
//        List<UsuarioEntity> todosUsuarios = usuarioRepository.findAll();
//
//        // Usar um HashSet para remover duplicações
//        Set<UsuarioEntity> usuariosSemDuplicacao = new HashSet<>(todosUsuarios);
//
//        // Converter o Set de volta para uma lista e retornar
//        return usuariosSemDuplicacao.stream().collect(Collectors.toList());
//    }
//
//    @Transactional(readOnly = true)
//    public UsuarioEntity buscarPorEmail(String email) {
//        return usuarioRepository.findByEmail(email).orElseThrow(
//                () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "Usuário não encontrado")
//        );
//    }
}
