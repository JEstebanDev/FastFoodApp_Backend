/**
 * 
 */
package JEstebanC.FastFoodApp.service;

import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.sas.BlobSasPermission;
import com.azure.storage.blob.sas.BlobServiceSasSignatureValues;
import com.azure.storage.blob.specialized.BlockBlobClient;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.time.OffsetDateTime;

/**
 * @author Juan Esteban Castaño Holguin castanoesteban9@gmail.com 2022-04-14
 */
@Log4j2
@RequiredArgsConstructor
@Service
public class FileStorageService {
	private final BlobServiceClient blobServiceClient;


	public String Permiso() {
		String connString = "DefaultEndpointsProtocol=https;AccountName=fastfoodimage;AccountKey=ojh9bLmM7+RTDC2K87yoIc6pI0NxZ3766DgnCxvVIahgeW3U3YdtZl+Eof7EtABlqeqLXDHOHCRc+ASt0iDETw==;EndpointSuffix=core.windows.net";
        String containerName = "categoryimage";
        String blobName = "plate.jpg";
		BlobServiceClient client = new BlobServiceClientBuilder().connectionString(connString).buildClient();
		BlobClient blobClient = client.getBlobContainerClient(containerName).getBlobClient(blobName);

		BlobSasPermission blobSasPermission = new BlobSasPermission().setReadPermission(true); // grant read
																								// permission
																								// onmy
		OffsetDateTime expiryTime = OffsetDateTime.now().plusDays(2); // after 2 days expire
		BlobServiceSasSignatureValues values = new BlobServiceSasSignatureValues(expiryTime, blobSasPermission)
				.setStartTime(OffsetDateTime.now());

		return blobClient.getBlobUrl() + "?" + blobClient.generateSas(values);
	}

	public Boolean uploadAndDownloadFile(@NonNull MultipartFile file, String containerName) {
		boolean isSuccess = true;
		BlobContainerClient blobContainerClient = getBlobContainerClient(containerName);
		String filename = file.getOriginalFilename();
		BlockBlobClient blockBlobClient = blobContainerClient.getBlobClient(filename).getBlockBlobClient();
		try {
			// delete file if already exists in that container
			if (blockBlobClient.exists()) {
				blockBlobClient.delete();
			}
			// upload file to azure blob storage
			blockBlobClient.upload(new BufferedInputStream(file.getInputStream()), file.getSize(), true);
			String tempFilePath =  blockBlobClient.getBlobUrl();
		} catch (IOException e) {
			isSuccess = false;
			log.error("Error while processing file {}", e.getLocalizedMessage());
		}
		return isSuccess;
	}

	private @NonNull BlobContainerClient getBlobContainerClient(@NonNull String containerName) {
		// create container if not exists
		BlobContainerClient blobContainerClient = blobServiceClient.getBlobContainerClient(containerName);
		if (!blobContainerClient.exists()) {
			blobContainerClient.create();
		}
		return blobContainerClient;
	}
}
