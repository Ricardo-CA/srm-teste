package edu.srm.services;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import edu.srm.entities.Pessoa;
import edu.srm.repositories.PessoaRepository;
import edu.srm.services.exceptions.DatabaseException;
import edu.srm.services.exceptions.ResourceNotFoundException;

@Service
public class PessoaService {

	@Autowired
	private PessoaRepository repository;

	public List<Pessoa> findAll() {
		return repository.findAll();
	}

	public Pessoa findById(String id) {
		Optional<Pessoa> object = repository.findById(id);

		return object.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public Pessoa insert(Pessoa object) {

		return repository.save(object);
	}

	public Pessoa update(Pessoa object, String id) {
		try {
			Pessoa entity = repository.getById(id);

			entity.setNome(object.getNome());

			return repository.save(entity);
			
		} catch (EntityNotFoundException e) {
			throw new ResourceNotFoundException(e.getMessage());
		}
	}
	
	
	public void delete(String id) {
		try {
			repository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(e.getMessage());
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}

}
