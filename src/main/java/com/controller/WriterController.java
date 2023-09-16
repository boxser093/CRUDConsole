package com.controller;

import com.model.Writer;
import com.repository.impl.GsonWriterRepositoryImpl;

import java.util.List;

public class WriterController {

    public WriterController(GsonWriterRepositoryImpl gsonWriterRepository) {
        this.gsonWriterRepository = gsonWriterRepository;
    }

    private GsonWriterRepositoryImpl gsonWriterRepository;

    public Writer findById(Long id) {
        try {
            return gsonWriterRepository.getById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Writer[] findAll() {
        try {
            return gsonWriterRepository.getAll();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean saveLabel(Writer writer) {
        boolean result;
        try {
            result = gsonWriterRepository.save(writer);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(result){
            System.out.println("Label save successful!");
        }else {
            System.out.println("Label save unsuccessful!!!");
        }
        return result;
    }

    public boolean deleteById(Long id){
        boolean result;
        try {
            result = gsonWriterRepository.delete(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(result){
            System.out.println("Label deleted successful!");
        }else {
            System.out.println("Label deleted unsuccessful!!!");
        }
        return result;
    }

    public boolean updateLabel(Long id){
        boolean result;
        try {
            result = gsonWriterRepository.update(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(result){
            System.out.println("Label is updated data.");
        } else {
            System.out.println("Label is not updated.");
        }
        return result;
    }
}
