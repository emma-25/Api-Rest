package com.informatorio.emprendimiento.controller;

import com.informatorio.emprendimiento.dtos.VotoDto;
import com.informatorio.emprendimiento.entity.Emprendimiento;
import com.informatorio.emprendimiento.entity.Evento;
import com.informatorio.emprendimiento.entity.Usuario;
import com.informatorio.emprendimiento.entity.Voto;
import com.informatorio.emprendimiento.enums.VotoGenerado;
import com.informatorio.emprendimiento.repository.EmprendimientoRepository;
import com.informatorio.emprendimiento.repository.EventoRepository;
import com.informatorio.emprendimiento.repository.UsuarioRepository;
import com.informatorio.emprendimiento.service.VotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/voto")
public class VotoController {
    @Autowired
    private VotoService votoService;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private EmprendimientoRepository emprendimientoRepository;
    @Autowired
    private EventoRepository eventoRepository;
    private VotoDto votoDto;
    @PostMapping("crearVotoIdUsuario/{idUsuario}/IdEvento/{idEvento}/IdEmprendimiento/{idEmprendimiento}/StringGeneradoPor/{idGeneradoPor}")
    public ResponseEntity<?> altaVoto(
            @PathVariable("idUsuario") Long idUsuario,
            @PathVariable("idEvento") Long idEvento,
            @PathVariable("idEmprendimiento") Long idEmprendimiento,
            @PathVariable("idGeneradoPor") int idGeneradoPor){
        Evento evento = eventoRepository.getById(idEvento);
        Usuario usuario = usuarioRepository.getById(idUsuario);
        Emprendimiento emprendimiento = emprendimientoRepository.getById(idEmprendimiento);
        List<VotoGenerado> votoGenerado = List.of(VotoGenerado.mobile, VotoGenerado.web, VotoGenerado.servicio);
        Voto votoNuevo = new Voto();
        votoNuevo.setUsername(usuario);
        votoNuevo.setEvento(evento);
        votoNuevo.setVotoAEmprendimiento(emprendimiento);
        votoNuevo.setGeneradoPor(votoGenerado.get(idGeneradoPor));
        try {
            votoService.crearVoto(votoNuevo);
            return ResponseEntity.status(HttpStatus.CREATED).body(VotoDto.VotoAVotoDto(votoNuevo));
        }catch(Exception x){
            return new ResponseEntity<>("Datos incompletos o erróneos", HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/")
    public ResponseEntity<?> todosLosVotos(){
        List<Voto> votoLista = votoService.allVotos();
        if(!votoLista.isEmpty()){
            List<VotoDto> listaDto = new ArrayList<>();
            for (Voto s: votoLista) listaDto.add(VotoDto.VotoAVotoDto(s));
            return ResponseEntity.ok(listaDto);
        }
        return new ResponseEntity<>("No hay ningun voto", HttpStatus.NOT_FOUND);
    }
    @GetMapping(value = "buscarPorUsername/{username}")
    public ResponseEntity<?> votosUsername(@PathVariable String username){
        List<Voto> listaUsernameVoto = votoService.buscarVotoPorUsuario(username);
        if(!listaUsernameVoto.isEmpty()){
            List<VotoDto> listaDto = new ArrayList<>();
            for (Voto s: listaUsernameVoto) listaDto.add(VotoDto.VotoAVotoDto(s));
            return new ResponseEntity<>(listaDto, HttpStatus.OK);
        }
        return new ResponseEntity<>("El username no existe o no tiene votos", HttpStatus.NOT_FOUND);
    }
}