package com.demo.rest.modules.player.service;

import com.demo.rest.crypto.Pbkdf2PasswordHash;
import com.demo.rest.modules.player.entity.Player;
import com.demo.rest.modules.player.repository.api.PlayerRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.inject.Inject;
import lombok.NoArgsConstructor;
import jakarta.ws.rs.NotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
@NoArgsConstructor(force = true)
public class PlayerService {

    private final PlayerRepository repository;

    private final Pbkdf2PasswordHash passwordHash;

    // [pictures-in-files] get rid of
    private final String pictureDirPath = "pictureDirectory";
    private final Path pictureDirectory;

    @Inject
    public PlayerService(PlayerRepository repository, Pbkdf2PasswordHash passwordHash) {
        this.repository = repository;
        this.passwordHash = passwordHash;

        // [pictures-in-files] get rid of
        this.pictureDirectory = Paths.get(pictureDirPath);
        try {
            Files.createDirectories(this.pictureDirectory);
            System.out.println("directory created: " + pictureDirectory.toAbsolutePath());
            //clear directory
            Files.walk(pictureDirectory)
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try { Files.delete(file); }
                        catch (IOException e) { System.err.println("Could not delete file: " + file); }
                    });

        } catch (IOException e) {
            throw new IllegalStateException("exception when creating picture directory", e);
        }
    }

    public Optional<Player> find(UUID id) {
        return repository.find(id);
    }

    public Optional<Player> find(String login) {
        return repository.findByLogin(login);
    }

    public List<Player> findAll() {
        return repository.findAll();
    }

    @Transactional
    public void create(Player player) {
        player.setPassword(passwordHash.generate(player.getPassword().toCharArray()));
        repository.create(player);
    }

    public boolean verifyPassword(String login, String password) {
        return find(login)
                .map(player -> passwordHash.verify(password.toCharArray(), player.getPassword()))
                .orElse(false);
    }

    @Transactional
    public void updatePicture(UUID id, InputStream is) {
        repository.find(id).ifPresent(player -> {
            try {
                updatePictureFile(id, is);
                player.setPicture(is.readAllBytes());
                repository.update(player);
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        });
    }

    // [pictures-in-files] get rid of
    private void updatePictureFile(UUID id, InputStream is) throws IOException {
        String filename = id.toString() + ".jpg";
        Path picturePath = pictureDirectory.resolve(filename);

        try (OutputStream os = Files.newOutputStream(picturePath, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
        }

        System.out.println("Picture saved: " + picturePath.toAbsolutePath());
    }

    // [pictures-in-files] get rid of
    public byte[] getPicture(UUID id) {
        String filename = id.toString() + ".jpg";
        Path picturePath = pictureDirectory.resolve(filename);

        if (!Files.exists(picturePath)) {
            throw new NotFoundException("Picture not found for ID: " + id);
        }

        try {
            return Files.readAllBytes(picturePath);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    @Transactional
    // [pictures-in-files] get rid of
    private void deletePictureFile(UUID id) {
        String filename = id.toString() + ".jpg";
        Path picturePath = pictureDirectory.resolve(filename);

        try {
            if (Files.exists(picturePath)) {
                Files.delete(picturePath);
            }
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    public void deletePicture(UUID id) {
        repository.find(id).ifPresent(player -> {
                player.setPicture(new byte[0]);
                repository.update(player);
                deletePictureFile(id);
        });
    }

    @Transactional
    public void delete(UUID id) {
        repository.delete(repository.find(id).orElseThrow());
    }

    @Transactional
    public void update(Player player) {
       repository.update(player);
    }

}
