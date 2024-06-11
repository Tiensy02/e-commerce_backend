package SShop.mono.controler;

import SShop.mono.application.service.GenericCrudService;
import SShop.mono.application.service.user.ProductService;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

/**
 * api controller crud
 * @param <E> Entity
 * @param <D> Dto
 * @param <I> Type of id
 */
public class GenericCrudController<E, D, I> implements GenericCrudApi<D, I> {

  protected final GenericCrudService<E, I, D> service;

  public GenericCrudController(GenericCrudService<E,I,D> service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<?> get(int pageSize, int pageNumber) {
    try {
      if(pageNumber== 0 && pageSize == 0 ) {
        var result = service.get();
        return ResponseEntity.ok(result);
      }
      PageRequest page = PageRequest.of(pageNumber, pageSize);
      var result = service.getAll(page);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @Override
  public ResponseEntity<?> get(I id) {
    try {
          var result = service.get(id);
          return ResponseEntity.ok(result);
        }catch (NotFoundException e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }

  @Override
  public ResponseEntity<?> add(D d) {
    try {
          var result = service.save(d);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }

  @Override
  public ResponseEntity<?> update(I id,D d) {
    try {
          var result = service.update(id,d);
          return ResponseEntity.ok(result);
        }catch (Exception e){
          return ResponseEntity.badRequest().body(e.getMessage());
        }
  }

  @Override
  public ResponseEntity<?> delete(I id) {
   try {
         service.delete(id);
         return ResponseEntity.ok("success");
       }catch (Exception e){
         return ResponseEntity.badRequest().body(e.getMessage());
       }
  }
}
