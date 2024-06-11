package SShop.mono.controler;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public interface GenericCrudApi<D,I> {
  @GetMapping()
  ResponseEntity<?> get(@RequestParam int pageSize, @RequestParam int pageNumber);

  @GetMapping("/{id}")
  ResponseEntity<?> get(@PathVariable I id);

  @PostMapping("/add")
  ResponseEntity<?> add(@RequestBody @Valid D d);

  @PostMapping("/update/{id}")
  ResponseEntity<?> update(@PathVariable I id, @RequestBody @Valid D d);

  @PostMapping("/delete/{id}")
  ResponseEntity<?> delete(@PathVariable I id);
}
