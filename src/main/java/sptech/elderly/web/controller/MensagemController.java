package sptech.elderly.web.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sptech.elderly.service.MensagemService;
import sptech.elderly.web.dto.mensagem.MensagemInput;
import sptech.elderly.web.dto.mensagem.UsuarioConversaOutput;
import sptech.elderly.web.dto.mensagem.MensagemOutput;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/mensagens")
@RequiredArgsConstructor
public class MensagemController {
    private final MensagemService mensagemService;

    @PostMapping
    public ResponseEntity<MensagemOutput> postNovaMensagem(@RequestBody @Valid MensagemInput input) {
        return ResponseEntity.status(201).body(mensagemService.enviarMensagem(input));
    }

    @GetMapping("/{remetenteId}/{destinatarioId}")
    private ResponseEntity<List<MensagemOutput>> getMensagensComUsuario(@PathVariable Integer remetenteId, @PathVariable Integer destinatarioId) {
        return ResponseEntity.of(Optional.ofNullable(mensagemService.buscarMensagensEntreUsuarios(remetenteId, destinatarioId)));
    }

    @GetMapping("/conversas")
    public List<UsuarioConversaOutput> getConversas(@RequestParam Integer userId) {
        return null;
    }
}
