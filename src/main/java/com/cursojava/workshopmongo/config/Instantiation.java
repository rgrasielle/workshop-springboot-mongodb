package com.cursojava.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import com.cursojava.workshopmongo.domain.Post;
import com.cursojava.workshopmongo.domain.User;
import com.cursojava.workshopmongo.dto.AuthorDTO;
import com.cursojava.workshopmongo.repository.PostRepository;
import com.cursojava.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	// Injetar o UserRepository para fazer a operação com o banco de dados
	@Autowired 
	private UserRepository userRepository;
	
	@Autowired 
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();  // limpa a coleção de usuários no MongoDB
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		// Instanciar posts associado com o autor
		
		Post post1 = new Post(null, sdf.parse("21/03/2018"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("23/03/2018"), "Bom dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.getPosts().addAll(Arrays.asList(post1, post2));  // adiciona os posts ao usuário maria
		userRepository.save(maria);
	}
} 
