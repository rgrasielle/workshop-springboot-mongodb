package com.cursojava.workshopmongo.resources;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.cursojava.workshopmongo.domain.User;
import com.cursojava.workshopmongo.dto.UserDTO;
import com.cursojava.workshopmongo.services.UserService;

@RestController
@RequestMapping(value = "/users")
public class UserResource {
	
	@Autowired
	private UserService service;
	
	// Método para retornar uma lista de usuários
	
	@RequestMapping(method=RequestMethod.GET)  // ou @GetMapping
	public ResponseEntity<List<UserDTO>> findAll() {
		List<User> list = service.findAll(); // busca os usuários no banco e guarda na lista
		List<UserDTO> listDto = list.stream().map(x -> new UserDTO(x)).collect(Collectors.toList());  // converte uma lista de User para UserDTO
		return ResponseEntity.ok().body(listDto);
	}
	
	// Método para retornar o usuário por id
	
	@RequestMapping(value="/{id}", method=RequestMethod.GET)  // caminho: /users/{id}
	public ResponseEntity<UserDTO> findById(@PathVariable String id) {
		User obj = service.findById(id);
		return ResponseEntity.ok().body(new UserDTO(obj));  // a resposta será o obj convertido para UserDTO
	}
	
	// Método para inserir um usuário
	
		@RequestMapping(method=RequestMethod.POST)  // ou @PostMapping
		public ResponseEntity<Void> insert(@RequestBody UserDTO objDto) {
			User obj = service.fromDTO(objDto);  // converte o UserDTO para User
			obj = service.insert(obj);  // insere no banco de dados
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();  // pega a url do novo recurso inserido
			return ResponseEntity.created(uri).build();  // retorna o código HTTP 201 
		
		}
	
}
   