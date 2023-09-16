package com.controller;

import com.model.Label;
import com.model.LabelData;
import com.repository.impl.GsonLabelRepositoryImpl;

public class LabelController {
    private GsonLabelRepositoryImpl gsonLabelRepository;

    public LabelController(GsonLabelRepositoryImpl gsonLabelRepository) {
        this.gsonLabelRepository = gsonLabelRepository;
    }

    public Label findById(long id) {
        try {
            return gsonLabelRepository.getById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Label[] findAll() {
        try {
            Label[] result = gsonLabelRepository.getAll();
            return gsonLabelRepository.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public boolean saveLabel(LabelData label) {
        boolean result;
        try {
            result = gsonLabelRepository.save(label);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (result) {
            System.out.println("Label save successful!");
        } else {
            System.out.println("Label save unsuccessful!!!");
        }
        return result;
    }

    public boolean deleteById(Long id) {
        boolean result;
        try {
            result = gsonLabelRepository.delete(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (result) {
            System.out.println("Label deleted successful!");
        } else {
            System.out.println("Label deleted unsuccessful!!!");
        }
        return result;
    }

    public boolean updateLabel(Long id) {
        boolean result;
        try {
            result = gsonLabelRepository.update(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (result) {
            System.out.println("Label is updated data.");
        } else {
            System.out.println("Label is not updated.");
        }
        return result;
    }
}
