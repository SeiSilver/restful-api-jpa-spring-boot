package restdemo.restservice.restController;

import org.springframework.web.bind.annotation.*;
import restdemo.restservice.entity.Product;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@RestController
public class BasicRestAPI {

    private ArrayList<Product> list;

    @PostConstruct
    private void dataInit() {

        list = new ArrayList<>();
        list.add(new Product("1", "a"));
        list.add(new Product("2", "b"));
        list.add(new Product("3", "c"));
        list.add(new Product("4", "d"));
    }

    @GetMapping("/products")
    public List<Product> showAllProduct() {

        return list;
    }

    @GetMapping("/products/{id}")
    public Product getProduct(@PathVariable String id) {

        for (var i : list) {
            if (i.getId().equalsIgnoreCase(id)) {
                return i;
            }
        }

        return null;
    }


    @PostMapping("/products")
    public Product addProduct(@RequestParam(value = "id", defaultValue = "0") String id,
                              @RequestParam(value = "name", defaultValue = "default name") String name) {

        list.add(new Product(id, name));
        return new Product(id, name);
    }

    @DeleteMapping("/products/{id}")
    public List<Product> deleteProduct(@PathVariable String id) {

        for (var i : list) {
            if (i.getId().equalsIgnoreCase(id)) {
                list.remove(i);
                break;
            }
        }


        return list;
    }

}
