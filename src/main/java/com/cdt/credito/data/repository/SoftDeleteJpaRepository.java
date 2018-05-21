package com.cdt.credito.data.repository;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface SoftDeleteJpaRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {

	//Override CrudRepository or PagingAndSortingRepository's query method:
	@Override
	@Query("select e from #{#entityName} e where e.excluido=false")
	public List<T> findAll();

	//Look up deleted entities
	@Query("select e from #{#entityName} e where e.excluido=true")
	public List<T> recycleBin(); 

	//Soft delete.
	@Query("update #{#entityName} e set e.excluido=true where e.id=?1")
	@Modifying
	public void softDelete(ID id); 
	
	@Query("select e from #{#entityName} e where e.id=?1 and e.excluido=false")
	public T findOne(ID id);
}
