package com.cdt.credito.auth.repository;

import com.cdt.credito.auth.model.Usuario;
import com.cdt.credito.data.repository.SoftDeleteJpaRepository;

public interface UsuarioRepository extends SoftDeleteJpaRepository<Usuario, Long> {

	Usuario findFirstByEmailIgnoreCaseAndExcluidoFalse(String email);

}
