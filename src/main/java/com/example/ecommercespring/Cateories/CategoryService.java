package com.example.ecommercespring.Cateories;

import com.example.ecommercespring.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepo categoryRepo;

    List<CategoryEntity> findAllCategories() {
        List<CategoryEntity> categories= categoryRepo.findAll();
        if (categories.size()==0){
            throw new UserNotFoundException("Categories Not Found");
        }else {
            return categories;
        }
    }
}
