package SShop.mono.application.service;

import SShop.mono.application.utils.ObjectUtils;
import java.util.List;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public abstract class GenericCrudImplSpringMongoDb<E,I,D> implements GenericCrudService<E,I,D> {

  protected final MongoRepository<E,I> repository;

  protected GenericCrudImplSpringMongoDb(MongoRepository<E, I> repository) {
    this.repository = repository;
  }
  @Override
  public List<E> get() {
    List<E> result = repository.findAll();
    return result;
  }
  @Override
  public E get(I id) throws NotFoundException {
    E entity = repository.findById(id).orElseThrow(()-> new NotFoundException());
    return entity;
  }

  @Override
  public Page<E> getAll(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  public E save(D dto) throws NotFoundException {
    E entity  = this.convertDtoToEntity(dto);
    return repository.save(entity);
  }

  @Override
  public E update(I id, D dto) throws NotFoundException {
    E entity =  repository.findById(id).orElseThrow(()-> new NotFoundException());
    ObjectUtils.copyNonNullProperties(dto,entity);
    return repository.save(entity);

  }

  @Override
  public void delete(I id) throws NotFoundException {
    E entity = repository.findById(id).orElseThrow(() -> new NotFoundException());
    repository.save(entity);
  }
}
