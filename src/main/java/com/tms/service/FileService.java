package com.tms.service;

import com.tms.exception.CustomFileException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
public class FileService {

    private final Path ROOT_FILE_PATH = Paths.get("data");

    public void upload(MultipartFile file) throws CustomFileException {
        try {
            Files.copy(file.getInputStream(), ROOT_FILE_PATH.resolve(file.getOriginalFilename()));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CustomFileException(e.getMessage());
        }
    }

    public Resource getFile(String filename) throws CustomFileException {
        Path path = ROOT_FILE_PATH.resolve(filename);

        try {
            Resource resource = new UrlResource(path.toUri());
            if (!resource.exists()) {
                throw new FileNotFoundException(filename);
            }
            if (resource.isReadable()) {
                return resource;
            }
            throw new CustomFileException("File is not readable");
        } catch (MalformedURLException e) {
            log.error(e.getMessage());
            throw new CustomFileException(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public List<String> getFiles() throws IOException {
        return Files.walk(ROOT_FILE_PATH)
                .filter(path -> !path.equals(ROOT_FILE_PATH))
                .map(Path::toString)
                .map(filename -> filename.substring(ROOT_FILE_PATH.toString().length() + 1))
                .toList();
    }

    public boolean deleteFile(String filename) throws FileNotFoundException {
        Path path = ROOT_FILE_PATH.resolve(filename);

        File file = new File(path.toUri());

        if (file.exists()) {
            return file.delete();
        }
        throw new FileNotFoundException(filename);
    }
}
