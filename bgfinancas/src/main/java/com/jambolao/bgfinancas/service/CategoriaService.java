package com.jambolao.bgfinancas.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jambolao.bgfinancas.model.categoria.Categoria;
import com.jambolao.bgfinancas.model.categoria.CategoriaRepository;

@Service
public class CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Transactional
    public Categoria createCategoria(Categoria categoria) {
        return repository.save(categoria);
    }

    @Transactional
    public List<String> listCategorias() {
        return repository.findAll()
                .stream()
                .map(Categoria::getNomeCategoria)
                .toList(); 
    }

    @Transactional
    public Categoria updateCategoria(Long id, Categoria categoria) {
        categoria.setId(id);
        return repository.save(categoria);
    }

    @Transactional
    public boolean deleteCategoria(Long id) {
        Optional<Categoria> existingCategoria = repository.findById(id);
        if (existingCategoria.isPresent()) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public Categoria readCategoria(Long id) {
        if(repository.existsById(id)) {
            return repository.findById(id).get();
        }
        return null;
    }

    @Transactional
    public Optional<Categoria> findByNameCategoria(String nomeCategoria) {
       
        return repository.findByNomeCategoria(nomeCategoria);
       
    }

}

// O erro ocorre porque você está tentando salvar uma entidade Movimentacao que possui uma associação obrigatória (não-nula)
// com uma entidade Categoria, mas essa instância de Categoria não foi salva antes. Isso é conhecido
// como problema de "transient instance", onde o Hibernate exige que qualquer entidade relacionada
// seja persistida antes de salvá-la como referência em outra entidade. Neste caso, a associação 
// categoria em Movimentacao está referenciando uma Categoria que ainda não está salva no banco de dados.