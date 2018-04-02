package com.schoenbrot.store.api.storeapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.Collection;

@RepositoryRestResource
interface ProductRepository extends JpaRepository<Product, Long> {
    Collection<Product> findByType(@Param("type") String type);
}

@SpringBootApplication
public class StoreApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StoreApiApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(ProductRepository productRepository) {
        return args -> {
            Arrays.asList("vape,vacuum,echo,bread".split(",")).forEach(type -> productRepository.save(new Product(type)));

            productRepository.findAll().forEach(System.out::println);

            productRepository.findByType("echo").forEach(System.out::println);
        };
    }
}

@Entity
class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String type;

    Product() {
    }

    Product(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return "Product{ id=" + this.id + ", type=" + this.type + "}";
    }
}