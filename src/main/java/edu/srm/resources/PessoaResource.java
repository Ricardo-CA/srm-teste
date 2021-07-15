package edu.srm.resources;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import edu.srm.entities.Pessoa;
import edu.srm.services.PessoaService;

@RestController
@RequestMapping(value = "/pessoas")
public class PessoaResource {

	@Autowired
	private PessoaService service;
	
	@GetMapping
	public ResponseEntity<List<Pessoa>> findAll () {
		List<Pessoa> list = service.findAll();
		
		return ResponseEntity.ok(list);
	}
	@GetMapping(value = "/{id}")
	public ResponseEntity<Pessoa> findById(@PathVariable String id) {
		Pessoa obj = service.findById(id);
		return ResponseEntity.ok().body(obj);
	}
	
	@PostMapping
	public ResponseEntity<Pessoa> insert(@Valid @RequestBody Pessoa obj) {
		obj.setIdentificador(obj.getIdentificador().trim().replace(".","").replace("/", "").replace("-", ""));
		obj.setTipoIdentificador(obj.getIdentificador().length() == 11 ? "CPF" : (obj.getIdentificador().length() == 14) ? "CNPJ": null);
		obj = service.insert(obj);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(obj.getIdentificador()).toUri();		
		return ResponseEntity.created(uri).body(obj);
	}
	
	@DeleteMapping(value = "/{id}")
	public ResponseEntity<Void> delete(@PathVariable String id) {
		
		service.delete(id);
		return ResponseEntity.noContent().build();
	}
	
	@PutMapping(value = "/{id}")
	public ResponseEntity<Pessoa> update(@PathVariable String id, @RequestBody Pessoa obj) {
		obj = service.update(obj, id);
		return ResponseEntity.ok(obj);
	}
}
