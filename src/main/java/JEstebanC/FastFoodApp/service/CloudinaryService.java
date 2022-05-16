/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-05-15
 */
public class CloudinaryService {

	Cloudinary cloudinary;

	public CloudinaryService() {
		cloudinary = new Cloudinary(ObjectUtils.asMap("cloud_name", "di4wu7js0", "api_key", "252375724828993",
				"api_secret", "CnGL4xvdKlWPcQP51ZnovHr-fA0", "secure", true));
	}

	@SuppressWarnings("rawtypes")
	public String upload(MultipartFile multipartFile, String folder) throws IOException {
		// get the name without the extension for instance: png jpg
		String nameImage = multipartFile.getOriginalFilename().split("\\.")[0];
		File file = convert(multipartFile);
		Map params = ObjectUtils.asMap("public_id", folder + "/" + nameImage, "overwrite", true);
		Map result = cloudinary.uploader().upload(file, params);
		file.delete();
		return result.get("url").toString();
	}

	@SuppressWarnings("rawtypes")
	public Map delete(String id, String folder) throws IOException {
		Map result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
		return result;
	}

	public File convert(MultipartFile multipartFile) throws IOException {
		File file = new File(multipartFile.getOriginalFilename());
		FileOutputStream fo = new FileOutputStream(file);
		fo.write(multipartFile.getBytes());
		fo.close();
		return file;
	}

}
