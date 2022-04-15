/**
 * 
 */
package JEstebanC.FastFoodApp.storage;

import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.common.StorageSharedKeyCredential;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;
/**
 * @author Juan Esteban Castaño Holguin
 * castanoesteban9@gmail.com
 * 2022-04-14
 */

@Configuration
public class AzureBlobStorageConfig {
	
	@Value("${azure.storage.blob-endpoint}")
    private String blobEndpoint;
	
    @Value("${azure.storage.account-name}")
    private String accountName;

    @Value("${azure.storage.account-key}")
    private String accountKey;

    @Bean
    public BlobServiceClient getBlobServiceClient() {
        return new BlobServiceClientBuilder()
                .endpoint(String.format(Locale.ROOT, blobEndpoint, accountName))
                .credential(new StorageSharedKeyCredential(accountName, accountKey))
                .buildClient();
    }
}