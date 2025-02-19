package ru.practicum.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.category.mapper.CategoryMapper;
import ru.practicum.category.model.Category;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto getCategoryById(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new NotFoundException("Категория с id \"" + categoryId + "\" не найдена"));
        return CategoryMapper.categoryToCategoryDto(category);
    }

    @Override
    public List<CategoryDto> getAllCategories(PageRequest pageRequest) {
        List<Category> categories = categoryRepository.findAll(pageRequest).toList();
        return categories.stream().map(CategoryMapper::categoryToCategoryDto).collect(Collectors.toList());
    }

    @Override
    public CategoryDto addCategory(NewCategoryDto newCategoryDto) {
        Category category = CategoryMapper.newCategoryDtoToCategory(newCategoryDto);
        Category newCategory = categoryRepository.save(category);
        return CategoryMapper.categoryToCategoryDto(newCategory);
    }

    @Override
    public void deleteCategory(Long categoryId) {
        categoryRepository.findById(categoryId).orElseThrow(()
                -> new NotFoundException("Категория с id \"" + categoryId + "\" не найдена"));
        categoryRepository.deleteById(categoryId);
    }

    @Override
    public CategoryDto updateCategory(Long categoryId, NewCategoryDto newCategoryDTO) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(()
                -> new NotFoundException("Категория с id \"" + categoryId + "\" не найдена"));
        category.setName(newCategoryDTO.getName());
        Category updatedCategory = categoryRepository.save(category);
        return CategoryMapper.categoryToCategoryDto(updatedCategory);
    }
}
