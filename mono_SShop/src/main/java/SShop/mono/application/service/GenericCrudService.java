package SShop.mono.application.service;

import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * CRUD service
 * @param <E> Entity
 * @param <I> Type of ID
 * @param <D> Dto
 */
public interface GenericCrudService<E, I, D> {

  List<E> get();

  E get(I id) throws NotFoundException;

  Page<E> getAll(Pageable pageable);

  E save(D dto) throws NotFoundException;

  E update(I id, D dto) throws NotFoundException;

  void delete(I id) throws NotFoundException;

  E convertDtoToEntity(D dto);

  D convertEntityToDto(E entity);


}
