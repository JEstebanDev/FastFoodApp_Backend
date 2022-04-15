/**
 * 
 */
package JEstebanC.FastFoodApp.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import JEstebanC.FastFoodApp.model.Response;
import JEstebanC.FastFoodApp.service.FileStorageService;

import java.time.Instant;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-04-14
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {

	@Autowired
	private final FileStorageService fileStorageService;
	
	@PostMapping(value = "/category",produces = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE })
	public ResponseEntity<?> uploadAndDownload(@RequestParam("file") MultipartFile file) {
		try {
			if (fileStorageService.uploadAndDownloadFile(file, "categoryimage")) {
//				final ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(
//						Paths.get(fileStorageService.getFileStorageLocation() + "/" + file.getOriginalFilename())));
				return ResponseEntity.status(HttpStatus.OK).body("ok");
			}
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).message("Error while processing file")
					.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		} catch (Exception e) {
			return ResponseEntity.ok(Response.builder().timeStamp(Instant.now()).message("Error while processing file")
					.status(HttpStatus.BAD_REQUEST).statusCode(HttpStatus.BAD_REQUEST.value()).build());
		}
	}
}