package com.ciprian.demo.controller;

import com.ciprian.demo.domain.LoaderEvent;
import com.ciprian.demo.enums.LoaderStatusEnum;
import com.ciprian.demo.repository.LoaderEventRepository;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Objects;

@Controller
@RequestMapping("/")
public class FileUploadController {

  private static final String UPLOAD_DIR = "C:\\Users\\ccsicsek\\Workspace\\demo\\mavendemo\\src\\main\\resources\\uploads\\";
  private static final String REDIRECT_URL = "redirect:/";

  private final LoaderEventRepository loaderEventRepository;

  public FileUploadController(LoaderEventRepository loaderEventRepository) {
    this.loaderEventRepository = loaderEventRepository;
  }

  @GetMapping
  String getHomePage() {
    return "index";
  }

  @PostMapping("upload")
  public String uploadFile(@RequestParam("file") MultipartFile file, RedirectAttributes attributes) {
    try {
      if (loaderEventRepository.countAllByStatusEquals(LoaderStatusEnum.R) > 0) {
        attributes.addFlashAttribute("error", "Egy betöltés már folyamatban van, próbálja meg később.");
        return REDIRECT_URL;
      }

      if (file.isEmpty()) {
        attributes.addFlashAttribute("message", "Kérem válassz ki a felöltetni kívánt fájlt.");
        return REDIRECT_URL;
      }

      if (!Arrays.asList("xls", "xlsx").contains(FilenameUtils.getExtension(
          Objects.requireNonNull(file.getOriginalFilename()).toLowerCase()))) {
        attributes.addFlashAttribute("error", "Csak excel dokumentumokat tölthet fel.");
        return REDIRECT_URL;
      }

      // normalize the file path
      String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));

      LoaderEvent loaderEvent = new LoaderEvent().setStatus(LoaderStatusEnum.R);
      LoaderEvent savedLoaderEvent = loaderEventRepository.save(loaderEvent);

      Path path = Paths.get(UPLOAD_DIR, fileName);
      Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

      // return success response
      attributes.addFlashAttribute("message", "Sikeres fájlfeltöltés " + fileName + '!');

      return REDIRECT_URL;
    } catch (IOException e) {
      attributes.addFlashAttribute("error", e.getMessage());
      return REDIRECT_URL;
    }
  }

}
