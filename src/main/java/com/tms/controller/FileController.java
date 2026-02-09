package com.tms.controller;

import com.tms.exception.CustomFileException;
import com.tms.service.FileService;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<HttpStatus> upload(@RequestParam("file_key") MultipartFile file) throws CustomFileException {
        fileService.upload(file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{filename}")
    public ResponseEntity<Resource> getFile(@PathVariable("filename") String filename) throws CustomFileException {
        Resource resource = fileService.getFile(filename);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=\"" + resource.getFilename() + "\"");
        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }

    @Tag(name = "Удаление")
    @DeleteMapping("/{filename}")
    public ResponseEntity<HttpStatus> delete(@PathVariable("filename") String filename) throws CustomFileException, FileNotFoundException {
        if (fileService.deleteFile(filename)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }

    @Hidden
    @GetMapping
    public ResponseEntity<List<String>> getFiles() throws IOException {
        List<String> filenames = fileService.getFiles();
        return new ResponseEntity<>(filenames, HttpStatus.OK);
    }
}
