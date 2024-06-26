package org.springcloud.msvc.usuarios.controllers;

import org.springcloud.msvc.usuarios.models.entity.Usuario;
import org.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @GetMapping
    public List<Usuario>listar(){
        return service.listar();
    }

   @GetMapping("/{id}")
   public ResponseEntity<?> detalle(@PathVariable Long id){

       Optional<Usuario>usuarioOptional = service.porId(id);
       if(usuarioOptional.isPresent()){
           return ResponseEntity.ok(usuarioOptional.get());
       }
       return ResponseEntity.notFound().build();
   }

   @PostMapping
    public ResponseEntity<?>crear(@RequestBody Usuario usuario){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
   }

   @PutMapping("/{id}")
   public ResponseEntity<?>editar(@RequestBody Usuario usuario, @PathVariable Long id){
       Optional<Usuario> userOpt = service.porId(id);
       if(userOpt.isPresent()){
         Usuario usuarioDb = userOpt.get();
           usuarioDb.setNombre(usuario.getNombre());
           usuarioDb.setEmail(usuario.getEmail());
           usuarioDb.setPassword(usuario.getPassword());
           return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuarioDb));
       }
       return ResponseEntity.notFound().build();
   }

   @DeleteMapping("{/id}")
    public ResponseEntity<?>elimimar(@PathVariable Long id){
        Optional<Usuario> userOpt = service.porId(id);
        if(userOpt.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
   }
}
