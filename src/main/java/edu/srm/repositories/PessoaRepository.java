package edu.srm.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import edu.srm.entities.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, String>{

}
